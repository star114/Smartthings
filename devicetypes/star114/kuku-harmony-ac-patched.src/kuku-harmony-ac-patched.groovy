/**
 *  KuKu Harmony (Patched) - Virtual Switch for Logitech Harmony (Patched)
 *
 *  Copyright 2017 KuKu <turlvo@gmail.com> / 2020 star114
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

metadata {
    definition (name: "KuKu Harmony_AC (Patched)", namespace: "star114", author: "KuKu/star114", ocfDeviceType: "oic.d.airconditioner", vid: "generic-thermostat") {
        capability "Actuator"
        capability "Switch"
        capability "Thermostat Mode"
        capability "Thermostat Cooling Setpoint"
        capability "Fan Speed"
        capability "Refresh"
        capability "Sensor"
        capability "Configuration"
        capability "Health Check"

        command "mode"
        command "jetcool"
        command "speedChange"
        command "tempup"
        command "tempdown"

        attribute "prevTemperature", "number"
        attribute "coolFanSpeed", "number"
        attribute "dehumidifierFanSpeed", "number"
    }

    // for classic app
    tiles(scale: 2) {
        standardTile ("actionFlat", "device.switch", width: 2, height: 2, decoration: "flat") {
            state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState:"turningOn"
            state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00a0dc", nextState:"turningOff"
            state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState:"turningOn"
            state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00a0dc", nextState:"turningOff"
        }

        standardTile ("mode", "device.mode", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "mode", label: "MODE", action: "mode", defaultState: true
        }
        standardTile ("jetcool", "device.jetcool", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "jetcool", label: "JET MODE", action: "jetcool", defaultState: true
        }
        standardTile ("speedChange", "device.speedChange", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "speedChange", label: "FAN SPEED", action: "speedChange", defaultState: true
        }
        standardTile("tempup", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "tempup", label:'up', action:"tempup", defaultState: true
        }
        standardTile("tempdown", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "tempdown", label:'down', action:"tempdown", defaultState: true
        }
    }
}

def installed() {
    log.debug "installed()"
    //configure()
    // set to default
    sendEvent(name: "switch", value: "off", displayed: true)
    sendEvent(name: "supportedThermostatModes", value:["rush hour", "heat", "cool", "off"])
    sendEvent(name: "ThermostatMode", value: "off")
    sendEvent(name: "coolingSetpoint", value: 24 as int, unit: "C")
    sendEvent(name: "prevTemperature", value: 24 as int)
    sendEvent(name: "fanSpeed", value: 0 as int)
    sendEvent(name: "coolFanSpeed", value: 2 as int)
    sendEvent(name: "dehumidifierFanSpeed", value: 3 as int)
}

// parse events into attributes
def parse(String description) {
    log.debug "Parsing '${description}'"
}

def getCurrentState(deviceName) {
    return device.currentState(deviceName)
}

def getThermostatMode() {
    def mode = getCurrentState("thermostatMode")?.value
    log.debug "getThermostatMode>> $mode"
    return mode
}

def getSwitchState() {
    def switchState = getCurrentState("switch")?.value
    log.debug "getSwitchState>> $switchState"
    return switchState
}

def getNumericAttribute(name) {
    def value = getCurrentState(name)?.numericValue
    log.debug "getNumericAttribute($name)>> $value"
    return value
}

def getFanSpeed() {
    def speed = getCurrentState("fanSpeed")?.value
    log.debug "getFanSpeed>> $speed"
    return speed
}

def mode() {
    log.debug "child mode()"
    parent.command(this, "mode")
}

def jetcool() {
    log.debug "child jetcool()"
    parent.command(this, "jetcool")
}

def speedChange() {
    log.debug "child speedChange()"
    parent.command(this, "speed")

}

def tempup() {
    log.debug "child tempup()"
    parent.command(this, "tempup")
}

def tempdown() {
    log.debug "child tempdown()"
    parent.command(this, "tempdown")
}

def coolpower() {
    log.debug "coolpower()"
    def currentThermostatMode = getThermostatMode()
    if (currentThermostatMode == "rush hour") {
        return
    } else if (currentThermostatMode == "off") {
        on()
    } else if (currentThermostatMode == "heat") {
        // dehumidifier -> cool
        mode()
    }

    sendEvent(name: "thermostatMode", value: "rush hour")
    sendEvent(name: "coolingSetpoint", value: 18 as int, unit: "C", displayed: true)
    sendEvent(name: "prevTemperature", value: 18 as int)
    sendEvent(name: "fanSpeed", value: 4 as int)
    sendEvent(name: "coolFanSpeed", value: 3 as int)
    jetcool()
}

def dehumidifier() {
    log.debug "dehumidifier()"
    def currentThermostatMode = getThermostatMode()
    if (currentThermostatMode == "heat") {
        return
    } else if (currentThermostatMode == "off") {
        on()
    } else if (currentThermostatMode == "rush hour") {
        // cool power -> cool
        mode()
    }

    sendEvent(name: "thermostatMode", value: "heat")
    sendEvent(name: "coolingSetpoint", value: 24 as int, unit: "C", displayed: true)
    sendEvent(name: "prevTemperature", value: 24 as int)
    def currentFanSpeed = getFanSpeed()
    sendEvent(name: "coolFanSpeed", value: currentFanSpeed as int)
    def currentDehumidifierFanSpeed = getNumericAttribute("dehumidifierFanSpeed")
    sendEvent(name: "fanSpeed", value: currentDehumidifierFanSpeed as int)
    mode()
}

def cool() {
    log.debug "cool()"
    def currentThermostatMode = getThermostatMode()
    if (currentThermostatMode == "cool") {
        return
    } else if (currentThermostatMode == "off") {
        on()
    } else {
        // dehumidifier / cool power
        sendEvent(name: "thermostatMode", value: "cool")
        sendEvent(name: "coolingSetpoint", value: 18 as int, unit: "C", displayed: true)
        sendEvent(name: "prevTemperature", value: 18 as int)
        def currentCoolFanSpeed = getNumericAttribute("coolFanSpeed")
        sendEvent(name: "fanSpeed", value: currentCoolFanSpeed as int)
        mode()
    }
}

def on() {
    log.debug "child on()"

    def currentState = getSwitchState()
    if (currentState == "on") {
        log.debug "Already turned on, skip ON command"
        return
    }

    parent.command(this, "power-on")
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "thermostatMode", value: "cool")
    // set to default
    sendEvent(name: "coolingSetpoint", value: 24 as int, unit: "C", displayed: true)
    sendEvent(name: "prevTemperature", value: 24 as int)
    sendEvent(name: "fanSpeed", value: 2 as int)
    sendEvent(name: "coolFanSpeed", value: 2 as int)
}

def off() {
    log.debug "child off()"

    def currentState = getSwitchState()
    if (currentState == "off") {
        log.debug "Already turned off, skip OFF command"
        return
    }

    parent.command(this, "power-off")
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "thermostatMode", value: "off")
    // set to default
    sendEvent(name: "coolingSetpoint", value: 24 as int, unit: "C", displayed: true)
    sendEvent(name: "prevTemperature", value: 24 as int)
    sendEvent(name: "fanSpeed", value: 0 as int)
    sendEvent(name: "coolFanSpeed", value: 2 as int)
}

def setCoolingSetpoint(temperature) {
    log.debug "setCoolingSetpoint($temperature)"
    def currentState = getSwitchState()
    if (currentState == "off") {
        log.debug "air conditioner is off"
        sendEvent(name: "coolingSetpoint", value: 24 as int, unit: "C", displayed: true)
        sendEvent(name: "prevTemperature", value: 24 as int)
        return
    }
    def prevTemperature = getNumericAttribute("prevTemperature")
    log.debug "prev temperature: $prevTemperature"
    def diff = prevTemperature - temperature
    log.debug "diff: $diff"
    if (diff > 0) {
        for (def i = 0; i < diff; i++) {
            tempdown()
        }
    } else if (diff < 0) {
        for (def i = 0; i > diff; i--) {
            tempup()
        }
    }
    sendEvent(name: "prevTemperature", value: temperature as int)
    sendEvent(name: "coolingSetpoint", value: temperature as int, unit: "C", displayed: true)
}

def setThermostatMode(mode){
    log.debug "setThermostatMode($mode)"

    switch(mode){
        case "rush hour":
            coolpower()
            break
        case "heat":
            dehumidifier()
            break
        case "cool":
            cool()
            break
        case "off":
            off()
            break
    }
}

def setFanSpeed(speed) {
    log.debug "setFanSpeed($speed)"
    def currentState = getSwitchState()
    if (currentState == "off") {
        log.debug "air conditioner is off"
        sendEvent(name: "fanSpeed", value: 0 as int)
        sendEvent(name: "coolFanSpeed", value: 2 as int)
        return
    }

    /*
     * 0 : off
     * 1 : low
     * 2 : medium
     * 3 : high
     * 4 : max
     */
    if (speed == 0) {
        log.debug "trying to be off"
        off()
        return
    } else if (speed == 4) {
        log.debug "trying to be max"
        coolpower()
        return
    }

    def currentThermostatMode = getThermostatMode()
    if (currentThermostatMode == "heat") {
        def prev = getNumericAttribute("dehumidifierFanSpeed")
        def diff = speed - prev
        log.debug "fan speed diff>> $diff"
        if (diff < 0) {
            diff = 3 + diff
        }
        for (def i = 0; i < diff; i++) {
            speedChange()
        }
        sendEvent(name: "dehumidifierFanSpeed", value: speed as int)
        sendEvent(name: "fanSpeed", value: speed as int, displayed: true)
    } else if (currentThermostatMode == "cool") {
        def prev = getNumericAttribute("coolFanSpeed")
        def diff = speed - prev
        log.debug "fan speed diff>> $diff"
        if (diff < 0) {
            diff = 3 + diff
        }
        for (def i = 0; i < diff; i++) {
            speedChange()
        }
        sendEvent(name: "coolFanSpeed", value: speed as int)
        sendEvent(name: "fanSpeed", value: speed as int, displayed: true)
    } else if (currentThermostatMode == "rush hour") {
        log.debug "cannot modify fan speed in cool power mode"
        sendEvent(name: "fanSpeed", value: 4 as int, displayed: true)
    } else {
        // off
        log.debug "must not reach here."
        sendEvent(name: "fanSpeed", value: 0 as int, displayed: true)
    }
}

def poll() {
    log.debug "poll()"
}

def parseEventData(Map results) {
    results.each { name, value ->
        //Parse events and optionally create SmartThings events
    }
}

def generateEvent(Map results) {
    results.each { name, value ->
        log.debug "generateEvent>> name: $name, value: $value"
        def currentState = getSwitchState()
        if (currentState != value) {
            log.debug "generateEvent>> changed to $value"
            sendEvent(name: "switch", value: value)
        } else {
            log.debug "generateEvent>> not change"
        }
    }
    return null
}

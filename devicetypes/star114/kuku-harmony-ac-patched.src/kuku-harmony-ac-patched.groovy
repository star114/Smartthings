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
    definition (name: "KuKu Harmony_AC (Patched)", namespace: "star114", author: "KuKu/star114") {
        capability "Actuator"
        capability "Switch"
        capability "Air Conditioner Fan Mode"
        capability "Air Conditioner Mode"
        capability "Thermostat Cooling Setpoint"
        capability "Refresh"
        capability "Sensor"
        capability "Configuration"
        capability "Health Check"

        command "mode"
        command "jetcool"
        command "speed"
        command "tempup"
        command "tempdown"

        command "virtualOn"
        command "virtualOff"
    }

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
        standardTile ("speed", "device.speed", width: 2, height: 2, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "speed", label: "FAN SPEED", action: "speed", defaultState: true
        }
        standardTile("tempup", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "tempup", label:'up', action:"tempup", defaultState: true
        }
        standardTile("tempdown", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "tempdown", label:'down', action:"tempdown", defaultState: true
        }
    }

    main("switch")
    details([
        "switch",
        "tempdown", "tempup",
        "mode", "jetcool", "speed"
    ])
}

def installed() {
    log.debug "installed()"
    //configure()
    state.switch="off"
    sendEvent(name: "switch", value: "off", displayed: true)
    sendEvent(name: "coolingSetpoint", value: 24, unit: "C")
    sendEvent(name: "supportedAcModes", value:["auto", "cool","dry","fanOnly"])
    sendEvent(name: "supportedAcFanModes", value:["auto", "low", "medium", "high", "turbo"])
    sendEvent(name: "airConditionerMode", value: "auto", displayed: false)
}

// parse events into attributes
def parse(String description) {
    log.debug "Parsing '${description}'"
}

def mode() {
    log.debug "child mode()"
    parent.command(this, "mode")
}

def jetcool() {
    log.debug "child jetcool()"
    parent.command(this, "jetcool")
}

def speed() {
    log.debug "child speed()"
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

def on() {
    log.debug "child on()"

    log.debug "on>> ${device.currentState("switch")?.value}"
    def currentState = device.currentState("switch")?.value

    if (currentState == "on") {
        log.debug "Already turned on, skip ON command"
    } else {
        parent.command(this, "power-on")
        sendEvent(name: "switch", value: "on")
    }
}

def off() {
    log.debug "child off()"

    log.debug "off>> ${device.currentState("switch")?.value}"
    def currentState = device.currentState("switch")?.value

    if (currentState == "on") {
        parent.command(this, "power-off")
        sendEvent(name: "switch", value: "off")

    } else {
        log.debug "Already turned off, skip OFF command"
    }
}

def virtualOn() {
    log.debug "child on()"
    sendEvent(name: "switch", value: "on")
}

def virtualOff() {
    log.debug "child off()"
    sendEvent(name: "switch", value: "off")
}

// handle commands
def setFanMode(mode) {
    log.debug "Executing 'setFanMode'"
    if (state.switch=="off") {
        log.debug "air conditioner is off"
        sendEvent(name: "fanMode", value: "auto", displayed: true)
        return
    }

    if (mode!="low" && mode!="medium" && mode!="high" && mode!="auto") {
        sendEvent(name: "fanMode", value: "auto", displayed: true)
        return
    }
    sendEvent(name: "fanMode", value: mode, displayed: true)
}

def setAirConditionerMode(mode) {
    log.debug "Executing 'setAirConditionerMode'"
    if (state.switch=="off") {
        log.debug "air conditioner is off"
        sendEvent(name: "airConditionerMode", value: "auto", displayed: true)
        return
    }

    if (mode!="cool" && mode!="auto" && mode!="fanOnly" && mode!="dry") {
        sendEvent(name: "airConditionerMode", value: "auto", displayed: true)
        return
    }
    sendEvent(name: "airConditionerMode", value: mode, displayed: true)
}

def setCoolingSetpoint(temperature) {
    log.debug "Executing 'setCoolingSetpoint'"
    if (state.switch=="off") {
        log.debug "air conditioner is off"
        sendEvent(name: "coolingSetpoint", value: 24 as int, unit: "C", displayed: true)
        return
    }

    sendEvent(name: "coolingSetpoint", value: temperature as int, unit: "C", displayed: true)
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
        def currentState = device.currentValue("switch")
        log.debug "generateEvent>> currentState: $currentState"
        if (currentState != value) {
            log.debug "generateEvent>> changed to $value"
            sendEvent(name: "switch", value: value)
        } else {
            log.debug "generateEvent>> not change"
        }
    }
    return null
}

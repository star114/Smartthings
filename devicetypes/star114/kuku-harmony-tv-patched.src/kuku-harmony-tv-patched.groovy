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
    definition (name: "KuKu Harmony_TV (Patched)", namespace: "star114", author: "KuKu/star114", ocfDeviceType: "oic.d.tv") {
        capability "Actuator"
        capability "Switch"
        capability "Audio Mute"
        capability "Audio Volume"
        capability "Tv Channel"
        capability "Button"
        capability "Media Input Source"
        capability "Refresh"
        capability "Sensor"
        capability "Configuration"
        capability "Health Check"

        command "volup"
        command "chup"
        command "mute_unmute"
        command "voldown"
        command "chdown"
        command "menu"
        command "home"
        command "input"
        command "back"
        command "number_1"
        command "number_2"
        command "number_3"
        command "number_4"
        command "number_5"
        command "number_6"
        command "number_7"
        command "number_8"
        command "number_9"
        command "number_0"

        command "up"
        command "down"
        command "left"
        command "right"
    }

    tiles (scale: 2){
        standardTile ("actionFlat", "device.switch", width: 2, height: 2, decoration: "flat") {
            state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState:"turningOn"
            state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00a0dc", nextState:"turningOff"
            state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState:"turningOn"
            state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00a0dc", nextState:"turningOff"
        }

        standardTile ("volup", "device.volup", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "volup", label: "Volume Up", action: "volup", defaultState: true
        }
        standardTile ("chup", "device.chup", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "chup", label: "Channel Up", action: "chup", defaultState: true
        }
        standardTile ("mute_unmute", "device.mute_unmute", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "mute_unmute", label: "Mute Unmute", action: "mute_unmute", defaultState: true
        }
        standardTile ("voldown", "device.voldown", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "voldown", label: "Volume Down", action: "voldown", defaultState: true
        }
        standardTile ("chdown", "device.chdown", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "chdown", label: "Channel Down", action: "chdown", defaultState: true
        }

        standardTile ("menu", "device.menu", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "menu", label: "MENU", action: "menu", defaultState: true
        }
        standardTile ("home", "device.home", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "home", label: "HOME", action: "home", defaultState: true
        }
        standardTile ("input", "device.input", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "input", label: "INPUT", action: "input", defaultState: true
        }
        standardTile ("back", "device.back", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "back", label: "BACK", action: "back", defaultState: true
        }

        standardTile ("number_1", "device.number_1", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_1", label: "1", action: "number_1", defaultState: true
        }
        standardTile ("number_2", "device.number_2", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_2", label: "2", action: "number_2", defaultState: true
        }
        standardTile ("number_3", "device.number_3", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_3", label: "3", action: "number_3", defaultState: true
        }
        standardTile ("number_4", "device.number_4", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_4", label: "4", action: "number_4", defaultState: true
        }
        standardTile ("number_5", "device.number_5", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_5", label: "5", action: "number_5", defaultState: true
        }
        standardTile ("number_6", "device.number_6", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_6", label: "6", action: "number_6", defaultState: true
        }
        standardTile ("number_7", "device.number_7", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_7", label: "7", action: "number_7", defaultState: true
        }
        standardTile ("number_8", "device.number_8", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_8", label: "8", action: "number_8", defaultState: true
        }
        standardTile ("number_9", "device.number_9", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_9", label: "9", action: "number_9", defaultState: true
        }
        standardTile ("number_0", "device.number_0", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "number_0", label: "0", action: "number_0", defaultState: true
        }

        standardTile ("up", "device.up", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "up", label: "Up", action: "up", defaultState: true
        }
        standardTile ("down", "device.down", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "down", label: "Down", action: "down", defaultState: true
        }
        standardTile ("left", "device.left", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "left", label: "Left", action: "left", defaultState: true
        }
        standardTile ("right", "device.right", width: 2, height: 1, decoration: "flat", canChangeIcon: false, canChangeBackground: false) {
            state "right", label: "Right", action: "right", defaultState: true
        }
    }

    main(["switch"])
    details(["switch", "volup", "chup",
            "mute_unmute", "voldown", "chdown",
            "menu", "home", "input", "back",
            "number_1", "number_2", "number_3",
            "number_4", "number_5", "number_6",
            "number_7", "number_8", "number_9",
            "number_0", "up", "down",
            "left", "right"])
}

def installed() {
    log.debug "installed()"
    //configure()
}

// parse events into attributes
def parse(String description) {
    log.debug "Parsing '${description}'"
}

def volup() {
    log.debug "child volup()"
    parent.command(this, "volup")
}

def chup() {
    log.debug "child chup()"
    parent.command(this, "chup")
}

def mute_unmute() {
    log.debug "child mute_unmute()"
    parent.command(this, "mute")
}

def voldown() {
    log.debug "child voldown()"
    parent.command(this, "voldown")
}

def chdown() {
    log.debug "child chdown()"
    parent.command(this, "chdown")
}

def menu() {
    log.debug "child menu()"
    parent.command(this, "menu")
}

def home() {
    log.debug "child home()"
    parent.command(this, "home")
}

def input() {
    log.debug "child input()"
    parent.command(this, "input")
}

def back(value) {
    log.debug "child back()"
    parent.command(this, "back")
}

def number_1(value) {
    log.debug "child number_1()"
    parent.commandValue(this, "1")
}

def number_2(value) {
    log.debug "child number_2()"
    parent.commandValue(this, "2")
}

def number_3(value) {
    log.debug "child number_3()"
    parent.commandValue(this, "3")
}

def number_4(value) {
    log.debug "child number_4()"
    parent.commandValue(this, "4")
}

def number_5(value) {
    log.debug "child number_5()"
    parent.commandValue(this, "5")
}

def number_6(value) {
    log.debug "child number_6()"
    parent.commandValue(this, "6")
}

def number_7(value) {
    log.debug "child number_7()"
    parent.commandValue(this, "7")
}

def number_8(value) {
    log.debug "child number_8()"
    parent.commandValue(this, "8")
}

def number_9(value) {
    log.debug "child number_9()"
    parent.commandValue(this, "9")
}

def number_0(value) {
    log.debug "child number_0()"
    parent.commandValue(this, "0")
}

def up() {
    log.debug "child up()"
    parent.command(this, "up")
}

def down() {
    log.debug "child down()"
    parent.command(this, "down")
}

def left() {
    log.debug "child left()"
    parent.command(this, "left")
}

def right() {
    log.debug "child right()"
    parent.command(this, "right")
}

// capability: Switch
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

// capability: Audio Mute
def setMute(state) {
    log.debug "setMute($state)"
}

def mute() {
    log.debug "mute()"
    mute_unmute()
}

def unmute() {
    log.debug "unmute()"
    mute_unmute()
}

// capability: Audio Volume
def setVolume(volume) {
    log.debug "setVolume($volume)"
}

def volumeUp() {
    log.debug "volumeUp()"
    volup()
}

def volumeDown() {
    log.debug "volumeDown()"
    voldown()
}

// capability: Tv Channel
def setTvChannel(channel) {
    log.debug "setTvChannel($channel)"
}

def channelUp() {
    log.debug "channelUp()"
    chup()
}

def channelDown() {
    log.debug "channelDown()"
    chdown()
}

// capability: Media Input Source
def setInputSource(mode) {
    log.debug "setInputSource($mode)"
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

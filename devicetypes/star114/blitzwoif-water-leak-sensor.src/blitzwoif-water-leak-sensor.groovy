/*
 *  BliTZWOIF Water Leak Sensor
 *  (forked from Orvibo Moisture Sensor)
 *
 *  Copyright 2018 SmartThings 2020 star114
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 *
 */

import physicalgraph.zigbee.clusters.iaszone.ZoneStatus
import physicalgraph.zigbee.zcl.DataType

metadata {
    definition(name: "BliTZWOIF Water Leak Sensor", namespace: "star114", author: "SmartThings/star114", vid: "generic-leak", mnmn:"SmartThings", ocfDeviceType: "x.com.st.d.sensor.moisture") {
        capability "Configuration"
        capability "Refresh"
        capability "Water Sensor"
        capability "Sensor"
        capability "Health Check"
        capability "Battery"

        fingerprint endpointId: "01", profileId: "0104", deviceId: "0402", inClusters: "0000, 0001, 0003, 0500, EF01", outClusters: "0003, 0019", manufacturer: "_TYZB01_o63ssaah", model: "TS0207", deviceJoinName: "BliTZWOIF Water Leak Sensor"
    }

    simulator {

        status "dry": "zone status 0x0020 -- extended status 0x00"
        status "wet": "zone status 0x0021 -- extended status 0x00"

    }

    tiles(scale: 2) {
        multiAttributeTile(name:"water", type: "generic", width: 6, height: 4){
            tileAttribute ("device.water", key: "PRIMARY_CONTROL") {
                attributeState "dry", icon:"st.alarm.water.dry", backgroundColor:"#ffffff"
                attributeState "wet", icon:"st.alarm.water.wet", backgroundColor:"#00a0dc"
            }
        }

        valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
            state "battery", label:'${currentValue}% battery', unit:""
        }

        standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", action: "refresh.refresh", icon: "st.secondary.refresh"
        }

        main "water"
        details(["water", "battery", "refresh"])
    }
}

def parse(String description) {
    log.debug "description: $description"

    def result
    Map map = zigbee.getEvent(description)

    if (!map) {
        if (description?.startsWith('zone status')) {
            map = getMoistureResult(description)
        } else if(description?.startsWith('enroll request')){
            List cmds = zigbee.enrollResponse()
            log.debug "enroll response: ${cmds}"
            result = cmds?.collect { new physicalgraph.device.HubAction(it) }
        } else {
            Map descMap = zigbee.parseDescriptionAsMap(description)
            if (descMap?.clusterInt == 0x0500 && descMap.attrInt == 0x0002) {
                map = getMoistureResult(description)
            } else if (descMap?.clusterInt == 0x0001 && descMap?.commandInt != 0x07 && descMap?.value) {
                if (descMap?.attrInt == 0x0021) {
                    map = getBatteryPercentageResult(Integer.parseInt(descMap.value, 16))
                } else {
                    map = getBatteryResult(Integer.parseInt(descMap.value, 16))
                }
            }
        }
    }
    if (map && !result) {
        result = createEvent(map)
    }
    log.debug "Parse returned $result"

    result
}

/**
 * PING is used by Device-Watch in attempt to reach the Device
 **/
def ping() {
    log.debug "ping()"
    refresh()
}

def refresh() {
    log.debug "refresh()"
    def refreshCmds = []
    refreshCmds += zigbee.readAttribute(zigbee.POWER_CONFIGURATION_CLUSTER, 0x0020) // zigbee.BATTERY_MEASURE_VALUE
    refreshCmds += zigbee.readAttribute(zigbee.IAS_ZONE_CLUSTER, zigbee.ATTRIBUTE_IAS_ZONE_STATUS) +
        zigbee.enrollResponse()

    refreshCmds
}

def installed() {
    log.debug "installed()"
    refresh()
}

def updated() {
    log.debug "updated()"
    refresh()
}

def uninstalled() {
    log.debug "uninstalled()"
}

def poll() {
    log.debug "poll()"
    refresh()
}

def configureHealthCheck() {
    log.debug "Configuring Health Check, Reporting"

    // Power configuration reporting time (max) = 21600 s = 360 mins = 6 hours
    // Device-Watch allows 2 check-in misses from device
    // 5 min lag time for communication
    Integer hcIntervalMinutes = 360 * 2 + 5
    def healthEvent = [name: "checkInterval", value: hcIntervalMinutes * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID]]
    sendEvent(healthEvent)
}

def configure() {
    log.debug "configure()"
    configureHealthCheck()

    def configCmds = []
    // battery reporting interval max: 21600 s
    configCmds += zigbee.batteryConfig(30, 21600, 0x01)
    refresh() + configCmds
}

def getMoistureResult(description) {
    ZoneStatus zs = zigbee.parseZoneStatus(description)
    def value = zs?.isAlarm1Set() ? "wet" : "dry"
    [
        name           : 'water',
        value          : value,
        descriptionText: "${device.displayName} is $value",
        translatable   : true
    ]
}

def getBatteryPercentageResult(rawValue) {
    log.debug "getBatteryPercentageResult()"
    def result = [:]

    if (0 <= rawValue && rawValue <= 200) {
        result.name = 'battery'
        result.translatable = true
        result.value = Math.round(rawValue / 2)
        result.descriptionText = "${device.displayName} battery was ${result.value}%"
    }

    log.debug "${device.displayName} battery was ${result.value}%"
    result
}

private Map getBatteryResult(rawValue) {
    log.debug 'getBatteryResult()'
    def linkText = getLinkText(device)

    def result = [:]

    def volts = rawValue / 10
    if (!(rawValue == 0 || rawValue == 255)) {
        def minVolts = 2.1
        def maxVolts = 3.0
        def pct = (volts - minVolts) / (maxVolts - minVolts)
        def roundedPct = Math.round(pct * 100)
        if (roundedPct <= 0)
            roundedPct = 1
        result.value = Math.min(100, roundedPct)
        result.descriptionText = "${linkText} battery was ${result.value}%"
        result.name = 'battery'

    }

    log.debug "${linkText} battery was ${result.value}%"
    return result
}

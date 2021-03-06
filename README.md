# Smartthings

Smartthings smartapps and custom DTHs

## Smart apps

### kuku-harmony-patched

For [Logitech Harmony IR hub](https://www.logitech.com/en-us/product/harmony-hub?crid=60)
* Virtual Switch for Logitech Harmony Hub using [harmony-api](https://github.com/maddox/harmony-api)
* Special thanks to turlvo. (forked from [KuKu Harmory by turlvo](https://github.com/turlvo/KuKuHarmony))
* Smart app for its custom DTHs
* Supports "Activity", "TV", and "Air Conditioner" at the beginning

## DTHs

### smartsense-temp-humidity-sensor-patched

For [Hejhome smart temperature-humidity sensor](https://www.hej.life/shop/?idx=27)
* Can connect to Smartthings hub directly. (Cloud execution)
* Special thanks to iquix. (forked from [iquix's repo](https://github.com/iquix/Smartthings/blob/master/devicetypes/iquix/smartsense-temp-humidity-sensor-patched.src/smartsense-temp-humidity-sensor-patched.groovy))
* Compatible with remaining battery capacity.

### zigbee-metering-plug-patched

For [Dawon DNS ZB smart plug 16A (end device)](https://pmshop.co.kr/product/detail.html?product_no=111&cate_no=33&display_group=1) and Dawon DNS ZB smart plug 16A (new device - router)
* Can connect to Smartthings hub directly. (Cloud execution) - Official zigbee-switch-power DTH will support Dawon DNS ZB smart plug (local execution) soon.
* Forked from [SmartThingsPublic repo](https://github.com/star114/SmartThingsPublic/blob/master/devicetypes/smartthings/zigbee-metering-plug.src/zigbee-metering-plug.groovy)
* Compatible with Dawon smart plugs footprints

### integrated-zigbee-switch-patched

For [Hejhome GoQual 3-Way ZigBee Switch](https://www.hej.life/shop/?idx=17)
* Can connect to Smartthings hub directly. (1 gang - Local execution, 2, 3 gang - Cloud execution)
* Special thanks to WooBooung. (forked from [WooBooung's repo](https://github.com/WooBooung/BooungThings/blob/master/devicetypes/woobooung/integrated-zigbee-switch.src/integrated-zigbee-switch.groovy))
* Compatible with several zigbee switch at the same time.

### hue-motion-sensor

For [Philips Hue Motion Sensor](https://www.philips-hue.com/en-us/p/hue-motion-sensor/046677473389)
* Forked from [bogdanalexe90's repo](https://github.com/bogdanalexe90/hueMotionSensor)

#### motion

Very snappy, works better than original smartthings motion sensor (even if the DTH runs in the cloud) having no delay or sleep time for reporting the motion events.

Also you can configure the following: Motion sensitivity (Low, Medium, High). Default is High

#### temperature

It's accurate and reported in time. You can also correct the readings by configuring an offset.

#### Illuminance

It's accurate and reported in time. You can also correct the readings by configuring an offset.

#### battery

Used the same logic as the smartthings motion sensor. Seems accurate.

### blitzwoif-water-leak-sensor

For [BlitzWolf BW-IS5 Zigbee Water Leak Sensor](https://ko.aliexpress.com/item/4000648797059.html?spm=a2g0o.productlist.0.0.46161eeeY4ne7r&algo_pvid=3afc4b5b-bf57-40f9-82f4-fbc8b5f23429&algo_expid=3afc4b5b-bf57-40f9-82f4-fbc8b5f23429-0&btsid=0bb0622c16020384037183783eb183&ws_ab_test=searchweb0_0,searchweb201602_,searchweb201603_)
* Forked from [SmartThingsPublic repo: Orvibo Moisture Sensor](https://github.com/SmartThingsCommunity/SmartThingsPublic/blob/5544550ac6ec5e1d6fbedd83ea340ce65262907b/devicetypes/smartthings/orvibo-Moisture-Sensor.src/orvibo-Moisture-Sensor.groovy)
* Compatible with blitzwoif water leak sensor footprint

### kuku-harmony-default-patched

Virtual Device for SmartApp - kuku-harmony-patched
* Default On/Off switch

### kuku-harmony-activity-patched

Virtual Device for SmartApp - kuku-harmony-patched
* Activity On/Off switch

### kuku-harmony-ac-patched

Virtual Device for SmartApp - kuku-harmony-patched
* Air Conditioner (AC) Switch + Device controller

### kuku-harmony-tv-patched

Virtual Device for SmartApp - kuku-harmony-patched
* TV Switch + Device controller

### smartweather-station-for-korea

Virtual Device for smart weather station from [Korea public data portal](https://data.go.kr)
* forked from [WooBooung's repo](https://github.com/WooBooung/BooungThings/blob/master/devicetypes/woobooung/smartweather-station-for-korea.src/smartweather-station-for-korea.groovy)

### messenger-telegram

Virtual Device for Telegram Messenger
* forked from [fison67's repo](https://github.com/fison67/Smartthings-DTH/blob/master/devicetypes/fison67/messenger-telegram.src/messenger-telegram.groovy)
* Not working correctly without workaround. (use https://msnoti.herokuapp.com not telegram api server directly)

<template>
    <div>
        <l-map ref="map"
               style="width: auto; z-index: 1; position: initial;"
               :style="{height: height}"
               :zoom="zoom"
               :center="getCenter(this.lat,this.lng)"
               @update:center="onMapDragEnd"
               @update:zoom="onZoomEnd">
            <l-tile-layer url="http://{s}.tile.osm.org/{z}/{x}/{y}.png"></l-tile-layer>
            <template v-for="dataLog in dataLogs">
                <l-marker ref="marker"
                          :lat-lng="getMarker(dataLog.lat,dataLog.lng)">
                    <l-popup>
                        <div>
                            {{getLabel(dataLog)}}
                        </div>
                        <div>
                            {{dataLog.fullName}}
                            <br/>
                            {{dataLog.contactNumber}}
                            <br/>
                            Coordinates: [{{dataLog.lat}},
                            {{dataLog.lng}}]
                            <br/>
                        </div>
                        <div v-if="dataLog.typeOfDisaster === 12 || dataLog.typeOfDisaster === 13 || dataLog.typeOfDisaster === 14">
                            {{dataLog.message}}
                        </div>
                        Reported {{changeDateFormat(dataLog.dateCreated)}}
                    </l-popup>
                    <l-icon
                            :icon-anchor="dynamicAnchor"
                            :icon-size="dynamicSize"
                            :popup-anchor="[0,-40]"
                            :icon-url="getMarkerIcon(dataLog)">
                    </l-icon>
                </l-marker>
            </template>
            <VGeosearch :options="geosearchOptions"/>
        </l-map>
    </div>
</template>

<script>
  import 'leaflet-geosearch/assets/css/leaflet.css'
  import {LMap, LTileLayer, LMarker, LIcon, LPopup} from 'vue2-leaflet'
  import L from 'leaflet'
  import '../../node_modules/leaflet/dist/leaflet.css'
  import {EventBus, events} from '../boot/event-bus'
  import {OpenStreetMapProvider} from 'leaflet-geosearch'
  import VGeosearch from 'vue2-leaflet-geosearch'
  import {date} from 'quasar'

  //Leaflet Marker Fix. Do not remove.
  delete L.Icon.Default.prototype._getIconUrl
  L.Icon.Default.mergeOptions({
    iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
    iconUrl: require('leaflet/dist/images/marker-icon.png'),
    shadowUrl: require('leaflet/dist/images/marker-shadow.png')
  })

  export default {
    name: 'leaflet-map',
    props: {
      currentLat: {
        type: Number,
        default: 0
      },
      currentLng: {
        type: Number,
        default: 0
      },
      onLocationLookup: {
        type: Function,
        default: () => {
        }
      },
      readOnly: {
        type: Boolean,
        default: false
      },
      defaultZoom: {
        type: Number,
        default: 13
      },
      height: {
        type: String,
        default: "90vh"
      },
      dataLogs: {
        type: Array,
        default: []
      }
    },
    components: {LMap, LTileLayer, LMarker, LIcon, LPopup, VGeosearch},
    data() {
      return {
        zoom: 13,
        url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        lat: 10.75816,
        lng: 122.5240497,
        geosearchOptions: {
          provider: new OpenStreetMapProvider(),
          style: 'button',
          popupFormat: ({query, result}) => {
            //inserted method to update marker in callback for updating format of popup of geosearch
            let e = {
              lng: result.x,
              lat: result.y
            }
            this.onMarkerDragEnd(e)
          },
          showMarker: false,
          maxMarkers: 0,
          retainZoomLevel: true,
          autoClose: true
        }
      }
    },
    computed: {
      dynamicSize() {
        return [this.iconSize * 1.25, this.iconSize * 1.45];
      },
      dynamicAnchor() {
        return [this.iconSize / 2, this.iconSize * 1.15];
      },
    },
    methods: {
      changeDateFormat(value, format = "MM/DD/YYYY HH:mm:ss") {
        return date.formatDate(value, format)
      },
      getMarkerIcon(dataLog) {
        switch (dataLog.typeOfDisaster) {
          case 0:
            return "statics/markers/fire_low.png"
          case 1:
            return "statics/markers/fire_moderate.png"
          case 2:
            return "statics/markers/fire_severe.png"
          case 3:
            return "statics/markers/mountain_low.png"
          case 4:
            return "statics/markers/mountain_moderate.png"
          case 5:
            return "statics/markers/mountain_severe.png"
          case 6:
            return "statics/markers/flood_low.png"
          case 7:
            return "statics/markers/flood_moderate.png"
          case 8:
            return "statics/markers/flood_severe.png"
          case 9:
            return "statics/markers/typhoon_low.png"
          case 10:
            return "statics/markers/typhoon_moderate.png"
          case 11:
            return "statics/markers/typhoon_severe.png"
          case 12:
            return "statics/markers/other_low.png"
          case 13:
            return "statics/markers/other_moderate.png"
          case 14:
            return "statics/markers/other_severe.png"
        }
      },
      getLabel(dataLog) {
        switch (dataLog.typeOfDisaster) {
          case 0:
            return "FIRE - LOW"
          case 1:
            return "FIRE - MODERATE"
          case 2:
            return "FIRE - SEVERE"
          case 3:
            return "EARTHQUAKE - LOW"
          case 4:
            return "EARTHQUAKE - MODERATE"
          case 5:
            return "EARTHQUAKE - SEVERE"
          case 6:
            return "FLOOD - LOW"
          case 7:
            return "FLOOD - MODERATE"
          case 8:
            return "FLOOD - SEVERE"
          case 9:
            return "TYPHOON - LOW"
          case 10:
            return "TYPHOON - MODERATE"
          case 11:
            return "TYPHOON - SEVERE"
          case 12:
            return "OTHER - LOW"
          case 13:
            return "OTHER - MODERATE"
          case 14:
            return "OTHER - SEVERE"
        }
      },
      onMarkerDragEnd(e) {
        if (!this.readOnly) {
          this.lat = parseFloat(e.lat)
          this.lng = parseFloat(e.lng)
          this.onLocationLookup(this.lat, this.lng)
        }
      },
      onMapDragEnd(e) {
        if (!this.readOnly) {
          this.lat = parseFloat(e.lat)
          this.lng = parseFloat(e.lng)
          this.onLocationLookup(this.lat, this.lng)
        }
      },
      onZoomEnd(e) {
        this.zoom = e
      },
      getMarker(lat, lng) {
        if (lat == null && lng == null) {
          return L.latLng(10.75816, 122.5240497)
        }
        return L.latLng(lat, lng)
      },
      getCenter(lat, lng) {
        if (lat == null && lng == null) {
          return L.latLng(10.75816, 122.5240497)
        }
        return [lat, lng]
      },
    },
    mounted() {
      /*
        This is needed to resize the map to fix issue loading the leaflet map
        https://github.com/KoRiGaN/Vue2Leaflet/issues/96
       */
      setTimeout(function () {
        window.dispatchEvent(new Event('resize'))
      }, 250)
      if (this.currentLat == null && this.currentLng == null) {
        this.lat = 10.75816
        this.lng = 122.5240497
      } else {
        this.lat = this.currentLat
        this.lng = this.currentLng
      }
      this.$nextTick(function () {
        let el = document.getElementsByClassName('leaflet-control-geosearch bar')
        for (let item of el) {
          item.style.width = '200px'
        }
      })
    },
    activated() {
      if (this.currentLat == null && this.currentLng == null) {
        this.lat = 10.75816
        this.lng = 122.5240497
      } else {
        this.lat = this.currentLat
        this.lng = this.currentLng
      }
    },
    created() {
      this.zoom = this.defaultZoom
      EventBus.$on(events.LEAFLET_MAP.EVENT_RESIZE, () => {
        setTimeout(function () {
          window.dispatchEvent(new Event('resize'))
        }, 250)
      })
    },
    beforeDestroy() {
      EventBus.$off(events.LEAFLET_MAP.EVENT_RESIZE)
    },
    watch: {
      currentLat(newVal, oldVal) {
        this.lat = newVal
      },
      currentLng(newVal, oldVal) {
        this.lng = newVal
      }
    }
  }
</script>


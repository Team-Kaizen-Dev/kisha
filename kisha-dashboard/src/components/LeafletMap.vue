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
            <l-marker ref="marker"
                      :draggable="true"
                      :lat-lng="getMarker(this.lat,this.lng)"
                      @update:latLng="onMarkerDragEnd"></l-marker>
            <VGeosearch :options="geosearchOptions"/>
        </l-map>
    </div>
</template>

<script>
  import 'leaflet-geosearch/assets/css/leaflet.css'
  import {LMap, LTileLayer, LMarker} from 'vue2-leaflet'
  import L from 'leaflet'
  import '../../node_modules/leaflet/dist/leaflet.css'
  import {EventBus, events} from '../boot/event-bus'
  import {OpenStreetMapProvider} from 'leaflet-geosearch'
  import VGeosearch from 'vue2-leaflet-geosearch'

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
      }
    },
    components: {LMap, LTileLayer, LMarker, VGeosearch},
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
    methods: {
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
      }
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


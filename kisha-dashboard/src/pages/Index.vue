<template>
    <q-page class="flex flex-center">
        <div class="full-width full-height">
            <leaflet-map
                    :read-only="false"
                    :current-lat="map.lat"
                    :current-lng="map.lng"
                    :onLocationLookup="setLatLng"
                    :default-zoom="9"/>
        </div>
    </q-page>
</template>

<script>
  import LeafletMap from "../components/LeafletMap";

  export default {
    name: 'PageIndex',
    components: {
      LeafletMap
    },
    data() {
      return {
        markers: [],
        map: {
          lat: 10.7474027,
          lng: 122.5340007
        }
      }
    },
    methods: {
      setLatLng(x, y) {
        this.map.lat = x
        this.map.lng = y
      },
    }
  }
</script>

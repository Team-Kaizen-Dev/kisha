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
  import SockJS from "sockjs-client";
  import Stomp from "webstomp-client";


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
      onStompConnected() {
        this.stompClient.subscribe('/topic/datalog/', this.addReport)
      },
      addReport(response) {
        console.log(JSON.parse(response.body))
      },
      initiateWebsocketConnection() {
        this.socket = new SockJS('/api/socket')
        this.stompClient = Stomp.over(this.socket)
        this.stompClient.debug = () => {
        }
        this.stompClient.connect({}, () => this.onStompConnected())
      }
    },
    created() {
      this.initiateWebsocketConnection()
    }
  }
</script>

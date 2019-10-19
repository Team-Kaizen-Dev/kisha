<template>
    <q-page class="flex flex-center">
        <div class="full-width full-height">
            <leaflet-map
                    :read-only="false"
                    :current-lat="map.lat"
                    :current-lng="map.lng"
                    :onLocationLookup="setLatLng"
                    :default-zoom="9"
                    :data-logs="dataLogs"/>
        </div>
    </q-page>
</template>

<script>
  import LeafletMap from "../components/LeafletMap";
  import SockJS from "sockjs-client";
  import Stomp from "webstomp-client";
  import {STORE_DATA_LOG_MODULE} from "../store/dataLog/constant";
  import {mapGetters, mapActions} from 'vuex'
  import {date} from 'quasar'
  import {EventBus, events} from '../boot/event-bus'


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
        },
        selectedDate: new Date(),
        dataLogs: [],
        typeFilter: {
          '0': true,
          '1': true,
          '2': true,
          '3': true,
          '4': true,
          '5': true,
          '6': true,
          '7': true,
          '8': true,
          '9': true,
          '10': true,
          '11': true,
          '12': true,
          '13': true,
          '14': true
        },
      }
    },
    methods: {
      ...mapActions(STORE_DATA_LOG_MODULE.MODULE_NAME, [STORE_DATA_LOG_MODULE.FIND_ALL]),
      setLatLng(x, y) {
        this.map.lat = x
        this.map.lng = y
      },
      legendFilter(value) {
        let typeFilters = []
        for (let i = 0; i < 15; i++) {
          if (this.typeFilter[i.toString()]) {
            typeFilters.push(i)
          }
        }
        return typeFilters.includes(value)
      },
      onStompConnected() {
        this.stompClient.subscribe('/topic/datalog', this.addReport)
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
      },
      findDataLogs(value) {
        let filters = [
          this.legendFilter,
        ]
        this.dataLogs = this[STORE_DATA_LOG_MODULE.GET_BY_DATE]({
          startDate: date.startOfDate(value, 'day'),
          endDate: date.endOfDate(value, 'day')
        }).filter(v => filters.every(f => f(v.typeOfDisaster)))
      }
    },
    computed: {
      ...mapGetters(STORE_DATA_LOG_MODULE.MODULE_NAME, [STORE_DATA_LOG_MODULE.GET_BY_DATE]),
    },
    async created() {
      this.initiateWebsocketConnection()
      EventBus.$on(events.DATE_SELECT.DATE_CHANGED, (value) => {
        this.selectedDate = value
        this.findDataLogs(value)
      })
      EventBus.$on(events.LEGEND_SELECT.LEGEND_SELECTED, (value) => {
        this.typeFilter = value
        this.findDataLogs(this.selectedDate)
      })
      try {
        await this[STORE_DATA_LOG_MODULE.FIND_ALL]()
        this.findDataLogs(new Date())
      } catch (error) {
        this.notifyError(error)

      }
    },
    beforeDestroy() {
      EventBus.$off(events.DATE_SELECT.DATE_CHANGED)
      EventBus.$off(events.LEGEND_SELECT.LEGEND_SELECTED)
    }
  }
</script>

<template>
    <q-list bordered separator>
        <q-item-label header>Data</q-item-label>
        <template v-for="dataLog in dataLogs">
            <q-item bordered clickable @click="goToMarker(dataLog)">
                <q-item-section avatar >
                    <q-avatar :style="{background:getColor(dataLog)}" text-color="white">
                        <q-icon :name="getIcon(dataLog)" color="white"/>
                    </q-avatar>
                </q-item-section>
                <q-item-section>
                    <q-item-label>{{dataLog.fullName}}</q-item-label>
                    <q-item-label>{{getLabel(dataLog)}}</q-item-label>
                </q-item-section>
                <q-item-section side top>
                    <q-item-label caption>{{changeDateFormat(dataLog.dateCreated)}}</q-item-label>
                </q-item-section>
            </q-item>
        </template>
    </q-list>
</template>

<script>

  import {date} from 'quasar'
  import {EventBus, events} from '../boot/event-bus'

  export default {
    props: {
      dataLogs: {
        type: Array,
        default: []
      }
    },
    data() {
      return {
        dataLogList: []
      }
    },
    methods: {
      changeDateFormat(value, format = "MM/DD/YYYY HH:mm:ss") {
        return date.formatDate(value, format)
      },
      getIcon(dataLog) {
        switch (dataLog.typeOfDisaster) {
          case 0:
          case 1:
          case 2:
            return "fas fa-fire"
          case 3:
          case 4:
          case 5:
            return "fas fa-mountain"
          case 6:
          case 7:
          case 8:
            return "fas fa-water"
          case 9:
          case 10:
          case 11:
            return "fas fa-wind"
          case 12:
          case 13:
          case 14:
            return "fas fa-exclamation-circle"
        }
      },
      getColor(dataLog) {
        switch (dataLog.typeOfDisaster) {
          case 0:
          case 3:
          case 6:
          case 9:
          case 12:
            return "#4caf50"
          case 1:
          case 4:
          case 7:
          case 10:
          case 13:
            return "#f2c037"
          case 2:
          case 5:
          case 8:
          case 11:
          case 14:
            return "#c10015"
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
      goToMarker(dataLog) {
        EventBus.$emit(events.LEAFLET_MAP.GO_TO_MARKER, {
          lat: dataLog.lat,
          lng: dataLog.lng,
          id: dataLog.id
        })
      }
    },
  }
</script>

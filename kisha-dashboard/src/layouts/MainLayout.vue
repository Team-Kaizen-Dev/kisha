<template>
    <q-layout view="hHh LpR fFf">
        <q-header elevated>
            <q-toolbar class="text-green-9 bg-white">
                <q-btn dense flat round icon="menu" @click="miniState = !miniState"/>
                <q-toolbar-title>
                    <span name="event" class="cursor-pointer">
                        <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                            <q-date color="green-8" v-model="dateSelected" @input="dateChanged"/>
                        </q-popup-proxy>
                        {{formatDate(dateSelected)}}
                    </span>
                </q-toolbar-title>
                <q-btn dense flat round icon="far fa-bell" @click="rightDrawerOpen = !rightDrawerOpen"/>
            </q-toolbar>
        </q-header>
        <q-drawer
                v-model="leftDrawerOpen"
                bordered
                :width="300"
                :breakpoint="1024"
                content-class="bg-grey-1"
                :mini="miniState">
            <div class="full-height">
                <LegendList :mini="miniState"/>
            </div>
        </q-drawer>

        <q-page-container style="background: #f5f9fc">
            <router-view/>
        </q-page-container>
        <q-drawer
                content-class="bg-grey-1"
                v-model="rightDrawerOpen"
                side="right"
                :width="225"
                :breakpoint="500"
                bordered>
            <div class="full-height">
                <NotificationList/>
            </div>
        </q-drawer>
    </q-layout>
</template>

<script>
  import NotificationList from '../components/NotificationList'
  import LegendList from '../components/LegendList'
  import {date} from 'quasar'
  import {EventBus, events} from '../boot/event-bus'

  export default {
    name: 'MainLayout',
    components: {
      NotificationList, LegendList
    },
    data() {
      return {
        dateSelected: new Date(),
        leftDrawerOpen: true,
        rightDrawerOpen: false,
        miniState: true,
      }
    },
    computed: {},
    methods: {
      formatDate(value, format = "MM/DD/YYYY") {
        return date.formatDate(value, format)
      },
      dateChanged(val) {
        this.$refs.qDateProxy.hide()
        EventBus.$emit(events.DATE_SELECT.DATE_CHANGED, val)
      }
    }
  }
</script>

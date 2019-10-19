import Vue from 'vue'

export const EventBus = new Vue()

export const events = {
  LEAFLET_MAP: {
    EVENT_RESIZE: 'EVENT_RESIZE_LEAFLET_MAP'
  },
  DATE_SELECT: {
    DATE_CHANGED: 'DATE_CHANGED'
  },
  LEGEND_SELECT: {
    LEGEND_SELECTED: 'LEGEND_SELECTED'
  }
}


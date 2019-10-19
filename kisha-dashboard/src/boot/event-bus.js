import Vue from 'vue'

export const EventBus = new Vue()

export const events = {
  LEAFLET_MAP: {
    EVENT_RESIZE: 'EVENT_RESIZE_LEAFLET_MAP',
    GO_TO_MARKER: 'GO_TO_MARKER'
  },
  DATE_SELECT: {
    DATE_CHANGED: 'DATE_CHANGED'
  },
  LEGEND_SELECT: {
    LEGEND_SELECTED: 'LEGEND_SELECTED'
  },
  NOTIFICATION: {
    NEW_DATA_LOG: 'NEW_DATA_LOG'
  },
  DATA_LOG: {
    DATA_LOG_GENERATED: 'DATA_LOG_GENERATED'
  }
}


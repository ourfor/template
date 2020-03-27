import { createStore } from 'redux'

const DB = {
	version: $pkg.version,
	date: new Date().toDateString(),
	owner: 'everyone',
	expire: 1000 * 60 * 40, // 40 minutes
}

function fn(state = DB, {type, ...rest}) {
	let result = null
	switch(type) {
		case 'update': {
			result = { ...state, update: Date.now()}
		}
		default: {
			result = state
		}
	}
	localStorage.setItem('data-store',JSON.stringify(result))
	return result
}

export const db = createStore(fn)
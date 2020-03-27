import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import { useState, useEffect } from 'react'
import { Provider } from 'react-redux'
import { Suspense, lazy } from 'react'

const PageHome = lazy(() => import(/* webpackChunkName: "page-home" */'../pages/home/home'))
const PageNews = lazy(() => import(/* webpackChunkName: "page-news" */'../pages/news/news'))

export function Root({store}) {
	return (
		<Provider store={store}>
			<Router>
				<Switch>
					<Suspense fallback={<p>加载中...</p>}>
					<Route exact strict path="/" component={PageHome} />
					<Route exact strict path="/news" component={PageNews} />
					</Suspense>
				</Switch>
			</Router>
		</Provider>
	)
}
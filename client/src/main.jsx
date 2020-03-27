import { render } from 'react-dom'

// import style sheet
import './sass/main.scss'

// global state and router
import { db } from './data/data'
import { Root } from './router/router'

const App = () => (
    <Root store={db} />
)

render(<App />, document.getElementById('main'))
import { useMemo, useState, useEffect } from 'react'
import axios from 'axios'
import { connect } from '../../data/connect'

function Home({ global, dispatch }) {
	const [count, setCount] = useState(0)
	const [value, setValue] = useState(1)

	log(global)
	log(dispatch)

	const version = useMemo(() => `${value} ${Date.now()}`,[value])


	return (
		<div>
			count: {count} <button onClick={() => setCount(count+1)}>增加</button>
			value: {value} <button onClick={() => setValue(value+1)}>增加</button>
			version: {version}
		</div>
	)
}

export default connect(Home)
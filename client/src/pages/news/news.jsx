import { connect } from '../../data/connect'

function News({ global, dispatch }) {
	return (
		<div className="page-news">
			这是新闻页面
		</div>
	)
}

export default connect(News)
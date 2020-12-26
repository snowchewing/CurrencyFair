import axios from 'axios'

const TRADE_MSG_REST_API_URL = 'http://localhost:8080/api/tradeMessage';

class TradeMessageService {

    getTradeMessage() {
        return axios.get(TRADE_MSG_REST_API_URL);
    }

}

export default new TradeMessageService();
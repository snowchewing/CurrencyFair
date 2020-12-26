import React from 'react';
import TradeMessageService from '../service/TradeMessageService';

class TradeMessageComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            tradeMessage:[]
        }
    }

    componentDidMount(){
        this.getData();

        setInterval(this.getData, 1000);
    }

    getData = () => {
        TradeMessageService.getTradeMessage().then((response) => {
            this.setState({ tradeMessage: response.data})
        });
    }

    render (){
        return (
            <div>
                <h1 className = "text-center"> Latest trade message</h1>
                <table className = "table table-striped">
                    <tbody>
                        <tr><td> User Id</td><td> {this.state.tradeMessage.userId}</td></tr>
                        <tr><td> Currency From</td><td> {this.state.tradeMessage.currencyFrom}</td></tr>
                        <tr><td> Currency To</td><td> {this.state.tradeMessage.currencyTo}</td></tr>
                        <tr><td> Amount Sell</td><td> {this.state.tradeMessage.amountSell}</td></tr>
                        <tr><td> Amount Buy</td><td> {this.state.tradeMessage.amountBuy}</td></tr>
                        <tr><td> Rate</td><td> {this.state.tradeMessage.rate}</td></tr>
                        <tr><td> Time Placed</td><td> {this.state.tradeMessage.timePlaced}</td></tr>
                        <tr><td> Originating Country</td><td> {this.state.tradeMessage.originatingCountry}</td></tr>
                    </tbody>
                </table>

            </div>

        )
    }
}

export default TradeMessageComponent
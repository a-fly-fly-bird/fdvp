from urllib.parse import urlencode
import requests
import time_tools
import datetime
import json

base_url = 'https://query2.finance.yahoo.com/v8/finance/chart/{0}'

headers = {
    'User-Agent': 'Mozilla/5.0 (MacBook Air; M1 Mac OS X 11_4) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Safari/604.1',
    'X-Requested-With': 'XMLHttpRequest',
}

data = {
    'formatted':True,
    'crumb':'yL7ZfYJ7dZS',
    'lang':'en-US',
    'region': 'US',
    'includeAdjustedClose': True,
    'interval': '1d',
    'period1': 1659916800,
    'period2': 1691452800,
    'events': 'capitalGain%7Cdiv%7Csplit',
    'useYfid': True,
    'corsDomain': 'finance.yahoo.com'
}

def get_one_year_stock_data(stock_code):
    year_ago = datetime.date.today() - datetime.timedelta(days=365)
    print('year_ago', year_ago)
    now_date = datetime.date.today()
    print('now_date', now_date)
    return get_stock_data(stock_code, year_ago, now_date)
    
def get_stock_data(stock_code, start_date, end_date):
    start_timestamp = time_tools.timestamp_of_someday_8am(start_date)
    end_timestamp = time_tools.timestamp_of_someday_8am(end_date)
    data['period1'] = start_timestamp
    data['period2'] = end_timestamp
    real_base_url = base_url.format(stock_code)
    print(real_base_url)
    try:  
        response = requests.get(real_base_url, headers=headers, params=data)
        print(response.url)
        print('response.status_code:', response.status_code)
        if response.status_code == 200:  
            return response.json()
    except requests.ConnectionError as e:
        print('Error', e.args)
        return None

if __name__ == '__main__':
    stock_code = 'AAPL'
    aapl_data = get_one_year_stock_data(stock_code)
    print(aapl_data)
    with open('data.json', 'w', encoding='utf-8') as file:
        file.write(json.dumps(aapl_data, indent=2, ensure_ascii=False))

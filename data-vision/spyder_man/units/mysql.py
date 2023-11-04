import pymysql  
import datetime

db = pymysql.connect(host='118.31.41.235',user='ty', password='tzz_mysql.lqsym_zzt', port=13306)
cursor = db.cursor()
cursor.execute('SELECT VERSION()')
data = cursor.fetchone()
print('Database version:', data)
cursor.execute("CREATE DATABASE IF NOT EXISTS financial_system DEFAULT CHARACTER SET utf8")  
db.close()

db = pymysql.connect(host='118.31.41.235',user='ty', password='tzz_mysql.lqsym_zzt', port=13306, db='financial_system')
cursor = db.cursor()
sql = '''
create table if not exists stocks
(
    name       varchar(50) default 'default' null,
    stock_code varchar(50) default 'default' null,
    date       date                          null,
    open       double      default 0         null,
    high       double      default 0         null,
    low        double      default 0         null,
    close      double      default 0         null,
    adj_close  double      default 0         null,
    volume     double      default 0         null,
    rol        double      default 0         null,
    id         int auto_increment
        primary key
)
    collate = utf8mb4_bin;
'''
cursor.execute(sql)
db.close()

data ={}

import json
with open('data.json', 'r') as file:
    str = file.read()
    data = json.loads(str)

# print(data)
# print(data['chart']['result'][0]['meta']['symbol'])

timestamps = data['chart']['result'][0]['timestamp']
# print(timestamps)

name = data['chart']['result'][0]['meta']['symbol']

db = pymysql.connect(host='118.31.41.235',user='ty', password='tzz_mysql.lqsym_zzt', port=13306, db='financial_system')
cursor = db.cursor()
sql = 'INSERT INTO stocks(name, stock_code, date, open, high, low, close, adj_close, volume) values(% s, % s, % s, % s, % s, % s, % s, % s, % s)'
for index, timestamp in enumerate(timestamps):
    date = datetime.datetime.fromtimestamp(timestamp)
    high = data['chart']['result'][0]['indicators']['quote'][0]['high'][index]
    close = data['chart']['result'][0]['indicators']['quote'][0]['close'][index]
    volume = data['chart']['result'][0]['indicators']['quote'][0]['volume'][index]
    open = data['chart']['result'][0]['indicators']['quote'][0]['open'][index]
    low = data['chart']['result'][0]['indicators']['quote'][0]['low'][index]
    adjclose = data['chart']['result'][0]['indicators']['adjclose'][0]['adjclose'][index]
    # print(index_timestamp, high, close, volume, open, low, adjclose)
    try:
        cursor.execute(sql, (name, name, date, open, high, low, close, adjclose, volume))
        db.commit()
    except pymysql.Error as e:
        # 处理插入异常
        print("插入数据时发生异常：", e)
    finally:
        db.rollback()
db.close()


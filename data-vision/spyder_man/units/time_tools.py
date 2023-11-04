import datetime

def timestamp_of_someday_8am(date: datetime.date):
    morning_time = datetime.time(8, 0)
    timestamp = datetime.datetime.combine(date, morning_time)
    return int(timestamp.timestamp())

def timestamp_of_today_8am():
    current_date = datetime.datetime.now().date()
    return timestamp_of_someday_8am(current_date)
    
if __name__ == '__main__':
    print('someday: ', timestamp_of_someday_8am(datetime.date(2021, 8, 1)))
    print('today: ', timestamp_of_today_8am())
    
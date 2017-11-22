# -*-coding:utf-8-*-

from bs4 import BeautifulSoup
import requests
import json
import hashlib
import re
import os
import time
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException,StaleElementReferenceException, WebDriverException
import urllib.request
'''
options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
options.add_argument("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
options.add_argument("lang=ko_KR") # 한국어!
driver = webdriver.Chrome('/Users/user/Desktop/project/intern/chromedriver', chrome_options=options)

urlReg = re.compile('(https:\/\/)(([\w\-\.])+(\/)?)+([\w\-\.])')
pNameReg = re.compile('(\d+_)+n.jpg')
pSubUrlReg = re.compile('\/p\/(\w|\-)+\/\?taken\-by\=(\w|\.)+')

driver.get('https://www.instagram.com/p/BVnztH0lPIO/?taken-by=adamsenatori')
time.sleep(1)


html = driver.page_source  # 페이지의 elements모두 가져오기
soup = BeautifulSoup(html, 'html.parser')  # BeautifulSoup사용하기
pictures = soup.select(
    ' div._4rbun > img')
#react-root > section > main > div > div > article > div._sxolz._mi48x > div > div > div._e3il2._pmuf1 > div._4rbun > img
#react-root > section > main > div > div > article > div._sxolz._mi48x > div > div > div._4rbun > img
context = soup.select('div._4a48i._277v9 > ul > li > span > span')[0].text
print(context)
like = soup.select(
                '#react-root > section > main > div > div > article > div._ebcx9 > section._1w76c._nlmjy > div > a > span')
if not len(like):
    like = soup.select('section._1w76c._nlmjy > div._3gwk6._nt9ow > span > span')
    #react-root > section > main > div > div > article > div._ebcx9 > section._1w76c._nlmjy > div > a > span
    #react-root > section > main > div > div > article > div._ebcx9 > section._1w76c._nlmjy > div > a > span
    #react-root > section > main > div > div > article > div._ebcx9 > section._1w76c._nlmjy > div > a > span
    #react-root > section > main > div > div > article > div._ebcx9 > section._1w76c._nlmjy > div > a > span
    #react-root > section > main > div > div > article > div._ebcx9 > section._1w76c._nlmjy > div > a > span
print(like[0].text)
pictureUrl = urlReg.search(str(pictures))
print(pictureUrl.group())
# download add
pictureName = pNameReg.search(pictureUrl.group()).group()
print(pictureName)

driver.quit()
'''
b = '1,231'
b = b.replace(',','')
print(int(b))
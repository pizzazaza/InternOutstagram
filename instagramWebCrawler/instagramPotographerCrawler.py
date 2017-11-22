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
from dbClass import DbClass
from datetime import datetime
import urllib.request

options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
options.add_argument("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
options.add_argument("lang=ko_KR") # 한국어!
driver = webdriver.Chrome('/Users/user/Desktop/project/intern/chromedriver', chrome_options=options)
#driver = webdriver.PhantomJS('/Users/sejun/Desktop/Project/naverIntern/phantomjs-2.1.1-macosx/bin/phantomjs')
# /Users/sejun/Desktop/Project/naverIntern/chromedriver
# /Users/sejun/Desktop/Project/naverIntern/phantomjs-2.1.1-macosx/bin/phantomjs
driver.implicitly_wait(3)

driver.get('about:blank')
driver.execute_script("Object.defineProperty(navigator, 'plugins', {get: function() {return[1, 2, 3, 4, 5];},});")

urlReg = re.compile('(https:\/\/)(([\w\-\.])+(\/)?)+([\w\-\.])')
pNameReg = re.compile('(\d+_)+n.jpg')
pSubUrlReg = re.compile('\/p\/(\w|\-)+\/\?taken\-by\=(\w|\.)+')

BASE_DIR = os.path.dirname(os.path.abspath(__file__))



#
potographers = ["heysp","lacma","artbasel","saatchi_gallery","tate","palchenkov","ronhaviv_vii","bkstreetart","pauloctavious","victrver","artnet"
,"philmoorephoto","danrubin","donaldweber","kirstenalana","edouphoto","tarahkreutz","robertclarkphoto","thiswildidea","andrewknapp","macieknabrdalik","danielberehulak"]

#dguttenfelder
'''
potographers = [
"fmuytjens","garethpon", "adamsenatori","samhorine","mikekus","hirozzzz","macenzo","residentgp_homewares","lavicvic","alexprager","seoul_nightview"
,"adamfergusonphoto","therealpeterlindbergh","amberulmer","natnacphotos","edkashi","cassandragiraldo","yellowbirdvisuals","ivankphoto","goop","mimi_brune","coffeenclothes"
,"janske","benlowy","othellonine","chrysti","shgbaker","kevinruss","skwii","jeffonline","brahmino","katieorlinsky","chrisburkard"
,"natalieoffduty","targetstyle","garypeppergirl","susiebubble","elainewelteroth","jacquiealexander","reallykindofamazing","bleubird","alyssainthecity","charliemay","inezandvinoodh"
,"joshualott","marcusbleasdale","lynseyaddario","sarafarid","andrewquilty","damirsagolj","andreeacampeanu","michaelchristopherbrown","glennagordon","wissamgaza","stevemccurryofficial"
,"heysp","lacma","artbasel","saatchi_gallery","tate","palchenkov","ronhaviv_vii","bkstreetart","pauloctavious","victrver","artnet"
,"philmoorephoto","danrubin","donaldweber","kirstenalana","edouphoto","tarahkreutz","robertclarkphoto","thiswildidea","andrewknapp","macieknabrdalik","danielberehulak" ]
                 
                 
                #77명 100개의 개시물 부터 7000개 까지 -> 한명당 120개 게시물, 9360개
'''

def imageDownload(path, url):
    req = urllib.request.urlopen(url)
    file_name = url.split('/')[-1]

    meta = req.info()
    file_length = int(meta["Content-Length"])

    #if file_length > 10485760:

    if not os.path.isdir(path):
        os.mkdir(path)
    f = open(path  + file_name, 'wb')
    file_size_dl = 0
    block_sz = 8192
    while True:
        buffer = req.read(block_sz)
        if not buffer:
            break

        file_size_dl += len(buffer)
        f.write(buffer)

    f.close()
    print("Downloading: %s Bytes: %s" % (file_name, file_length))
    return file_name, file_name, file_length

def commentOpen(xpath):
    driver.find_element_by_class_name('_56pd5')
    driver.find_element_by_xpath(xpath).click()

def login():
    driver.get('https://www.facebook.com/login.php?skip_api_login=1&api_key=124024574287414&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fv2.2%2Fdialog%2Foauth%3Fchannel%3Dhttps%253A%252F%252Fstaticxx.facebook.com%252Fconnect%252Fxd_arbiter%252Fr%252FZ2duorNoYeF.js%253Fversion%253D42%2523cb%253Df35216e5b4d1618%2526domain%253Dwww.instagram.com%2526origin%253Dhttps%25253A%25252F%25252Fwww.instagram.com%25252Ff1af0b5fa57a29c%2526relation%253Dopener%26redirect_uri%3Dhttps%253A%252F%252Fstaticxx.facebook.com%252Fconnect%252Fxd_arbiter%252Fr%252FZ2duorNoYeF.js%253Fversion%253D42%2523cb%253Dfcf2b49f74a108%2526domain%253Dwww.instagram.com%2526origin%253Dhttps%25253A%25252F%25252Fwww.instagram.com%25252Ff1af0b5fa57a29c%2526relation%253Dopener%2526frame%253Df4d96608667f2%26display%3Dpopup%26scope%3Dpublic_profile%252Cemail%26response_type%3Dtoken%252Csigned_request%26domain%3Dwww.instagram.com%26origin%3D1%26client_id%3D124024574287414%26ret%3Dlogin%26sdk%3Djoey%26logger_id%3D6ea1e55e-642e-b1d3-e3de-07a1b9d61471&cancel_url=https%3A%2F%2Fstaticxx.facebook.com%2Fconnect%2Fxd_arbiter%2Fr%2FZ2duorNoYeF.js%3Fversion%3D42%23cb%3Dfcf2b49f74a108%26domain%3Dwww.instagram.com%26origin%3Dhttps%253A%252F%252Fwww.instagram.com%252Ff1af0b5fa57a29c%26relation%3Dopener%26frame%3Df4d96608667f2%26error%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%26e2e%3D%257B%257D&display=popup&locale=en_US&logger_id=6ea1e55e-642e-b1d3-e3de-07a1b9d61471')
    driver.find_element_by_name('email').send_keys('zmvmcm@naver.com')
    driver.find_element_by_name('pass').send_keys('Btpwns123#')
    driver.find_element_by_xpath('//*[@id="u_0_0"]').click()

def htmlSoup():
    html = driver.page_source  # 페이지의 elements모두 가져오기
    return BeautifulSoup(html, 'html.parser')  # BeautifulSoup사용하기


def postListCrawl(potographer):
    driver.get("https://www.instagram.com/"+potographer)

    time.sleep(3)

    for i in range(1,4):
        driver.execute_script("document.getElementsByTagName('footer')[0].scrollIntoView()")
        time.sleep(30)

        #10분
    # scroll한번에 12개씩
    soup = htmlSoup()
    postSets = soup.select('#react-root > section > main > article > div > div._cmdpi > div')

    postList = []
    for postSet in postSets[4:]:

        for post in postSet.select('div > a'):
            postList.append(pSubUrlReg.search(str(post)).group())


    return postList

#userid, pic_path, pic_name, like, context
def postCrawl(postList, potographer, dbconn):
    crawlResults = []

    try:
        userSeq = dbconn.insertUser((potographer, potographer, datetime.now()))
    except Exception as e:
        userSeq = dbconn.selectUser((potographer))
        print(e)

    for subUrl in postList:
        driver.get('https://www.instagram.com' + subUrl)
        time.sleep(30)
        #60*120 120분 240분
        print('https://www.instagram.com' + subUrl),
        #try:
        html = driver.page_source  # 페이지의 elements모두 가져오기
        soup = BeautifulSoup(html, 'html.parser')  # BeautifulSoup사용하기
        pictures = soup.select('div._4rbun > img')
        try:
            context = soup.select('div._4a48i._277v9 > ul > li > span > span')[0].text
        except Exception as e:
            print("text가 하나도 작성안되어 있을 수도 있음")
            context = "."
            print(e)

        try:
            pictureUrl = urlReg.search(str(pictures)).group()
            pictureName = pNameReg.search(pictureUrl).group()
            crawlResults.append([pictureUrl, pictureName, context])
            path = '/Users/user/outstagram_app/2017/11/' + potographer + '/'
            thumnail_file, original_file, file_length = imageDownload(path, pictureUrl)
            postSeq = dbconn.insertPost((userSeq, context, datetime.now()))
            fileSeq = dbconn.insertFile((postSeq, path, thumnail_file, original_file, file_length))
            print("["+str(userSeq) + ", " + str(postSeq) + ", " + str(fileSeq) + "]")
        except Exception as e:
            print("post crawl exception")
            print("비디오 게시물이기 때문에 실패했을 수 도 있음")
            print(e)
            continue

    return crawlResults


def downLoadImage(imageUrl):
    img_data = requests.get(imageUrl).content
    #image path 설정, name 설정
    with open('image_name.jpg', 'wb') as handler:
        handler.write(img_data)


if __name__ == "__main__":
    login()
    driver.get("https://www.instagram.com/")

    tmp = driver.find_element_by_xpath('//*[@id="react-root"]/section/main/article/div[2]/div[1]/div/span/button').click()

    time.sleep(3)

    dbconn = DbClass({'host':'10.113.156.122', 'user':'sejun2', 'pw':'tpwns1', 'db':'outstagram'})

    dataSet = {}
    for potographer in potographers:
        #user table insert
        try:
            postList = postListCrawl(potographer)
            print(postList)
            dataSet[potographer] = postCrawl(postList, potographer, dbconn)
        except Exception as e:
            print("main exception")
            print(e)
            driver.quit()

    print(dataSet)


    driver.quit()

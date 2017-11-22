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
pIdReg = re.compile('(\d+_)+n.jpg')

BASE_DIR = os.path.dirname(os.path.abspath(__file__))




def commentOpen(xpath):
    driver.find_element_by_class_name('_56pd5')
    driver.find_element_by_xpath(xpath).click()

def login():
    driver.get('https://www.facebook.com/login.php?skip_api_login=1&api_key=124024574287414&signed_next=1&next=https%3A%2F%2Fwww.facebook.com%2Fv2.2%2Fdialog%2Foauth%3Fchannel%3Dhttps%253A%252F%252Fstaticxx.facebook.com%252Fconnect%252Fxd_arbiter%252Fr%252FZ2duorNoYeF.js%253Fversion%253D42%2523cb%253Df35216e5b4d1618%2526domain%253Dwww.instagram.com%2526origin%253Dhttps%25253A%25252F%25252Fwww.instagram.com%25252Ff1af0b5fa57a29c%2526relation%253Dopener%26redirect_uri%3Dhttps%253A%252F%252Fstaticxx.facebook.com%252Fconnect%252Fxd_arbiter%252Fr%252FZ2duorNoYeF.js%253Fversion%253D42%2523cb%253Dfcf2b49f74a108%2526domain%253Dwww.instagram.com%2526origin%253Dhttps%25253A%25252F%25252Fwww.instagram.com%25252Ff1af0b5fa57a29c%2526relation%253Dopener%2526frame%253Df4d96608667f2%26display%3Dpopup%26scope%3Dpublic_profile%252Cemail%26response_type%3Dtoken%252Csigned_request%26domain%3Dwww.instagram.com%26origin%3D1%26client_id%3D124024574287414%26ret%3Dlogin%26sdk%3Djoey%26logger_id%3D6ea1e55e-642e-b1d3-e3de-07a1b9d61471&cancel_url=https%3A%2F%2Fstaticxx.facebook.com%2Fconnect%2Fxd_arbiter%2Fr%2FZ2duorNoYeF.js%3Fversion%3D42%23cb%3Dfcf2b49f74a108%26domain%3Dwww.instagram.com%26origin%3Dhttps%253A%252F%252Fwww.instagram.com%252Ff1af0b5fa57a29c%26relation%3Dopener%26frame%3Df4d96608667f2%26error%3Daccess_denied%26error_code%3D200%26error_description%3DPermissions%2Berror%26error_reason%3Duser_denied%26e2e%3D%257B%257D&display=popup&locale=en_US&logger_id=6ea1e55e-642e-b1d3-e3de-07a1b9d61471')
    driver.find_element_by_name('email').send_keys('zmvmcm@naver.com')
    driver.find_element_by_name('pass').send_keys('Btpwns123#')
    driver.find_element_by_xpath('//*[@id="u_0_0"]').click()


if __name__ == "__main__":
    login()
    driver.get("https://www.instagram.com/")

    tmp = driver.find_element_by_xpath('//*[@id="react-root"]/section/main/article/div[2]/div[1]/div/span/button').click()

    time.sleep(1)
    #driver.get("https://www.instagram.com/sejun.kk/")
    #time.sleep(1)

    #driver.find_element_by_xpath('//*[@id="react-root"]/section/main/article/ul/li[3]/a').click()
    #driver.get("https://www.instagram.com/sejun.kk/following")
    #time.sleep(3)
    #driver.execute_script("console.log(document.getElementsByClassName('_gs38e')[0])")
    #popUpHtml = driver.page_source
    #soup = BeautifulSoup(popUpHtml, 'html.parser')
    #li = soup.select("section > main > article")
    #body > div: nth - child(11) > div > div >
    #pop up window 열기에서 안됨
    #start = 0
    #num = 0
    #print(li)
    ind = 0
    two = 1
    cou = 0

    for i in range(1,20):
        html = driver.page_source  # 페이지의 elements모두 가져오기
        soup = BeautifulSoup(html, 'html.parser')  # BeautifulSoup사용하기
        pictures = soup.select('article > div._sxolz > div > div')
        try:
            likes = soup.select('article > div._ebcx9 > section._1w76c._nlmjy > div')
            likes = likes[ind].text
        except NoSuchElementException:
            likes = 0

        try:
            try:
                driver.find_element_by_xpath(
                    '//*[@id="mainFeed"]/div/div/div[1]/div/article[' + str(ind + 1) + ']/div[2]/div[1]/ul/li/span/a').click()
            except WebDriverException:
                driver.find_element_by_xpath(
                    '//*[@id="mainFeed"]/div/div/div[1]/div/article[' + str(ind + 1) + ']/div[2]/div[1]/ul/li[1]/span/a').click()
            except Exception:
                driver.execute_script("document.getElementsByTagName('article')[" + str(ind + 1) + "].scrollIntoView()")
                if ind != 4:
                    ind = ind + 1
            #//*[@id="mainFeed"]/div/div/div[1]/div/article[5]/div[2]/div[1]/ul/li[1]/span/a
            #//*[@id="mainFeed"]/div/div/div[1]/div/article[5]/div[2]/div[1]/ul/li[2]/a
            htmltmp = driver.page_source
            soup = BeautifulSoup(htmltmp, 'html.parser')
            context = soup.select('article')
            context = context[ind]
            print(context.select('div._ebcx9 > div._4a48i._277v9 > ul > li > span > span')[0].text)
        except NoSuchElementException:
            htmltmp = driver.page_source
            soup = BeautifulSoup(htmltmp, 'html.parser')
            comment = soup.select('article')
            context = comment[ind]
            print(context.select('div._ebcx9 > div._4a48i._277v9 > ul > li > span > span')[0].text)
            #context = ""
        except Exception:
            driver.execute_script("document.getElementsByTagName('article')[" + str(ind + 1) + "].scrollIntoView()")
            if ind != 4:
                ind = ind + 1
        #print(context)
        #//*[@id="mainFeed"]/div/div/div[1]/div/article[3]/div[2]/div[1]/ul/li[1]/span/span
        ##mainFeed > div > div > div:nth-child(1) > div > article:nth-child(4) > div._ebcx9 > div._4a48i._277v9 > ul > li:nth-child(1) > span > span

        #_2g7d5 -> like id
        #_nzn1h -> like count
        #print(len(likes) )
        print(likes)
        # article > div._ebcx9 > section._1w76c._nlmjy > div > span > span
        #articles = pictures[start:]
        #/

        pictureUrl = urlReg.search(str(pictures[ind]))
        print(pictureUrl.group())
        pictureId = pIdReg.search(pictureUrl.group())
        print(pictureId.group())
        #print(ind+1)
        driver.execute_script("document.getElementsByTagName('article')[" + str(ind+1) + "].scrollIntoView()")
        if ind != 4:
            ind = ind+1

        time.sleep(1)
    driver.quit()

'''
        time.sleep(3)
        for article in articles:
            cou = cou + 1

            #like = likes[num]
            #print("like: "+str(len(like)))
            #driver.execute_script("document.getElementsByTagName('article')[" + str(start + num) + "].scrollIntoView()")

            #moreContext = driver.find_element_by_xpath('#//*[@id="mainFeed"]/div/div/div[1]/div/article['+str(start + num)+']/div[2]/div[1]/ul/li[1]/span/a').click()
            #moreComment = driver.find_element_by_xpath('/ *[ @ id = "mainFeed"]/div/div/div[1]/div/article['+str(start + num)+']/div[2]/div[1]/ul/li[2]/a').click()
            artStr = str(article)

            m = urlReg.search(artStr)
            print(m.group())
            m2 = pIdReg.search((m.group()))
            print(m2.group())
            num = num + 1



        driver.execute_script("document.getElementsByTagName('article')["+str(start + num - 1)+"].scrollIntoView()")
        start = num
        num = 0
        time.sleep(3)
'''
        #driver.execute_script("window.scrollTo(0,1109);")
    #print(cou)
    #driver.find_element_by_xpath('/html/body/div[3]/div/div/button').click()

    # /*[@id="frmNIDLogin"]

    # //*[@id="react-root"]/section/main/article/div[1]/div/div[1]
    # _sxolz
    # _ebcx9
    # body > div:nth-child(10) > div > div >
    # 좋아요 #mainFeed > div > div > div:nth-child(1) > div > article:nth-child(2) > div._ebcx9 > section._1w76c._nlmjy > div > a > span
    # id article > header > div._j56ec > div > div > a
    # body > div:nth-child(10) > div > div > div._o0j5z > div


    # hashtag body > div:nth-child(11) > div > div > div._o0j5z > div > article > div._ebcx9 > div._4a48i._277v9 > ul > li > span > a
    #
    # 댓글 더 보기
    # /html/body/div[3]/div/div/div[2]/div/article/div[2]/div[1]/ul/li[2]/a
    # 댓글 아이디 긁어오기
    # 해시태그 긁어 오기
    # 좋아요 파싱
    # 사진 아이디
    # 유저 아이디
    # 해시로 관리

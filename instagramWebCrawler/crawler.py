# -*-coding:utf-8-*-

import operator
import re
import time
from selenium import webdriver
from bs4 import BeautifulSoup
from urllib import parse

browser = webdriver.PhantomJS()
tagsDic = {}
paramAll = []
times = 10


def scrollevent():
    browser.execute_script("window.scrollTo(0, document.body.scrollHeight);")
    time.sleep(3)


def search(params):
    ret = []
    print(params)
    for param in params:
        # 주어진 params로 검색, 인코딩 진행
        # 스크롤 초기화 해봐야할것같음

        try:
            browser.get("https://www.instagram.com/explore/tags/" + parse.quote(param))
            browser.execute_script("window.scrollTo(0,0);")
            time.sleep(1)
            browser.find_element_by_css_selector("._1cr2e._epyes").click()
            time.sleep(2)

            # 게시물확보
            for i in range(0, 50):
                scrollevent()

            # 인스타그램 게시물 가져오기
            items = browser.find_elements_by_css_selector("._2di5p")
            print(len(items))

            # 게시물마다 탐색(alt에 텍스트 존재)
            for item in items:
                info = item.get_attribute("alt")
                sentence = info.split()
                # HashTAG 뽑아내기
                for splititem in sentence:
                    if splititem.startswith("#"):
                        tags = splititem.split("#")
                        for tag in tags:
                            if tag == " " or tag == "" or check(tag):
                                continue

                            if tag in tagsDic:
                                tagsDic[tag] = tagsDic[tag] + 1
                            else:
                                tagsDic[tag] = 1

        except Exception as e:
            print(e)

    sortedtags = sorted(tagsDic.items(), key=operator.itemgetter(1), reverse=True)
    i = 0
    print(paramAll)
    for sortedtag in sortedtags:
        if sortedtag[0] not in paramAll:
            ret.append(sortedtag[0])
            i += 1
            if i >= 10:
                break
    return ret


def check(tag):
    notuseful = ["습득폰", "주운폰", "먹스타그램", "일상", "맞팔", "데일리", "소통", "선팔", "셀스타그램", "셀카", "좋아요", "daily", "팔로우", "셀피",
                 "얼스타그램", "주말", "selfie", "ootd", "인스타그램", "맛스타그램", "사진", "소통해요", "선팔하면맞팔", "일상스타그램", "f4f",
                 "instagood", "옷스타그램", "럽스타그랩", "인스타", "패션", "먹방", "오늘", "instadaily"]
    regex = '[a-z]'
    regex2 = '[그램$]'
    regex3 = '[ㄱ-ㅎ]'

    return bool(re.search(regex3, tag)) or bool(re.search(regex, tag)) or bool(
        re.search(regex2, tag)) or tag in notuseful


# Main
# params = ["삼성", "갤럭시노트8", "갤럭시", "노트북", "LG", "V30", "에일리언웨어", "엘지", "SAMSUNG", "핸드폰"]
#먹스타그램 #맛스타그램 #술스타그램 #밥스타그램 #빵스타그램 #instafood #야식 #간식 #먹방 #치느님 #맛집 #회식#디저트
params = ['효도선물']
paramAll = params

for i in range(0, 1):
    params = search(params)
    paramAll = paramAll + params

dellist = []
for i in tagsDic.keys():
    if tagsDic[i] == 1:
        dellist.append(i)

for d in dellist:
    del tagsDic[d]
# for word in notuseful:
#     if word in list(tagsDic.keys()):
#         del tagsDic[word]

with open("tagsbody.txt", mode="w") as f:
    f.write(str(sorted(tagsDic.items(), key=operator.itemgetter(1), reverse=True)))
    print("save")

#https://scontent-icn1-1.cdninstagram.com/t51.2885-15/e35/ pictureID
#위에 주소는 팝업으로 사진을 뛰을 경우
#pictureID : 22159298_135649447056347_2177643221885124608_n.jpg

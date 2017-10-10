#Hadoop Mahout
##추천 문제
여태까지 사용자가 item에 대해 평가를 한 기록을 기반으로 사용자가 아직 사용하지 않은 item에 대한 사용자의 평가를 예측하는 문제, 마케팅, 타겟광고등의 영역에서 중요한 요소이다.
제한된 자원을 최대한 효율적으로 분배할 수 있는 방법
Netflix와 왓챠는 별점을 바탕으로 추천
아마존, 이베이 등은 클릭한 상품 기록을 통해 추천
페이스북은 좋아요를 바탕으로 페이지 추천, 타겟 광고를 보여준다.


##협업 필터링
- 빅데이터를 분류, 이를 기반으로 새로운 데이터에 대입하여 분류
- 사용자들의 과거 경향이 미래에도 그대로 유지될 것이라는 전제
###사용자 기반 협업 필터링
- 비슷한 선호도를 가지는 다른 고객들의 상품에 대한 평가에 근거하여 추천
- 비슷한 성향을 가진 이웃을 찾고, 그 이웃의 선호도를 이용
- 클러스터링, K-최대근접 이웃, 베이지안 네트워크
- 두 고객이 모두 평가를 한 상품이 있어야 한다
- 두 고객 사이에만 상관 관계를 구할 수 있다
- Matrix Factorization 알고리즘 이용

###아이템 기반 협업 필터링
- 과거에 좋아했던 상품과 비슷한 상품을 좋아하는 경항이 있다는 것을 기반
- 고객이 선호도를 입력한 기존 상품들과, 예측 하고자 하는 상품과의 유사도를 계산하여 선호도를 측정
- 고객들의 선호도만 이용, 고객간 유사도는 고려되지 않는다
- 아마존, 넷플릭스에서 상품추천에 사용
- Matrix Factorization 알고리즘 이용

협업 필터링 In JAVA

|이름|적용방법|제공 알고리즘|확장성|사례|
|---|---|---|---|---|
|Apache Mahout|쉬움|다양함|높음|많음|
|Lenskit|쉬움|제한적|보통|적음|
|EasyRec|매우 쉬움| 제한적|낮음|보통|
##Recommendation Completion 알고리즘 이용
- explicit feedback
	- 1~5점과 같이 정확한 수치로 feedback
- implicit feedback
	- 조회, 좋아요, 구매 등 의 feedback
	- 간접적인 평가
- Matrix Factorization
	- 아래 설명  
- baseline predictor
	- 사용자나 아이템의 baseline을 예측
	- convex optimization을 통해 parameter를 fitting하는 문제
	- temporal model과 결합하여 사용하면 성능 향상
- neighborhood method
	- 영화 사이의 distance term을 정의
	- distance를 통해 유사도 판별
	- baseline predictor와 같이 사용

		
###Matrix Factorization (MF)
- matrix completion 문제 해결 알고리즘
- global structue를 찾아서 recommendation problem을 해결
- 행렬 사진
	- 유저가 item에 대한 평가를 기록한 matrix
	- *은 아직 사용자가 평가하지 않은 데이터를 의미
		- 추천은 *의 값을 예측하는 문제
- 거대한 matrix를 더 작은 matrix의 곱으로 표현할 수 있다면 더 적은 값을 추측해서 전체 값을 추측할 수 있을 것이다.
	- Matrix R a by b, P a by k, Q k by b
	- row, column의 개수 k
	- R = PQ
	- k의 값이 클수록 에러는 낮지만 오버피팅 문제 발생 가능 
- 가정
	- original data matrix **R**가 low rank matrix 이다
	- 우리가 복원하는 **R̂** 역시 low rank 조건을 가지므로 constrained optimization 문제로 바꿔서 쓸 수 있게 된다.
	- optimal한 matrix completion의 objective function 표현 사진
	-  min rank(R̂ )  s.t.  Ω(rui−r̂ui)=0 ∀u,i
	-  Ω(**A**ui−**B**ui)는 matrix A와 B의 i,j 번째 element중 하나라도 비어있으면 0, 둘 다 element가 존재하면 둘의 차이로 정의된다.
- 풀이 법
	- rank condition이 convex optimization이 아니기 때문에 이 문제를 optimal하게 풀 수 없다. 
	- **convex relaxation**
		- rank comdition을 convex 조건으로 바꿔서 푸는 방법 (근사)
		- optimal한 solution을 찾을 수 있다는 것이 보장
		- 다른 문제로 해결하기 때문에 상황에 따라 의미가 없을 수 있다
	- **hyper-parameter**
		- bias term 추가
		- rank의 값을 직접 입력하고 RMSE를 minize하는 방법 
		- 단점으로 local optimal solution을 얻게 된다
		- 하지만 practical하게 잘 동작한다
- 단일 모델에 가장 우수한 성능을 보인다고 알려져있다(다른 모델에 비해 8% 정도까지 개선 가능)
- 평가
	- RMSE(Root Mean Squared Error)를 사용
	- 사진
	- 전체 평균과 각각의 정보가 얼마나 많이 차이가 나는가를 평가
	- predicted result와 ground truth label 비교 

	
###implicit feedback
- 간접적인 평가로 수치적으로 표현되지 않는다. 
	- 아마존의 구매, 조회
	- youtube의 재생, 반복 재생, 시청 횟수 등
- 두 가지 이슈 
	- nagative observation 
		- 선호하지 않는 item과 잠재적으로 흥미가 있는 item의 값이 모두 0이다.
		- 일부분의 데이터만 관측
		- 일부분의 positive 데이터와 거의 대부분의 negative obervation들에 의해 model이 overfitting된다.
	- confidence (신뢰성)
		- feedback에 대해 굉장히 많은 노이즈가 있다
		- 사용자가 상품을 구매했다고 해서 상품에 대해 긍정적으로 생각할 것이라고 기대할 수 없다. 
- **Collaborative Filtering for Implicit Feedback Datasets [2]**
 	- 사용자 u가 아이템 i를 선호하는지 하지 않는지 여부를 가르키는 preference vector **p**ui 정의
 	- **p**ui의 값은 sing(**r**ui)로 정의
 	- confidence level **c**ui를 통해 신뢰성을 보안 해준다. 
	 	- **c**ui = 1 + α**r**ui.
	 	- **c**ui = 1+αlog(1+**r**ui/ε)
		- **c**ui는 optimize해야 할 parameter가 아니라 한번 정의하면 변하지 않는 상수값이라 전체 알고리즘은 그대로 유지
		- α의 크기로 positive observation과 negative observation의 중요도를 조절
	- RMSE를 minimize하는 objective 설계
- **Logistic Matrix Factorization for Implicit Feedback Data [3]**
	- 음악 추천에 응용
	- 사용자 u가 아이템 i를 선호할 확률을 이용
	- MAP
- **Bayesian Personalized Ranking for Non-Uniformly Sampled Items [4]**
	- observaed pair-wise competition 문제를 푸는 baysian personalized ranking optimization을 제안하고 MF로 확장한 다음 negative observation을 adaptive하게 sample하는 방식으로 개선
	- 
- filter bubble : 내 입맛에 맞는 정보만 보여주고 나머지 정보는 감추어져 사용자의 감정 조장도 가능해 질 수 있다.


###Apache Mahout
- 하둡을 기반으로 맵 리듀스를 이용해 클러스터링, 분류, 분석작업 수행 가능
- 대용량 데이터 분석이 가능하며 확장성을 제공하는 기계 학습 라이브러리
- 기능
	- 협업 필터링
	- 분류
	- 클러스터링
	- 패턴 마이닝
	- 그 외에 회귀분석, 진화 알고리즘, 벡터 유사도 등 

###요구사항
- API
	- 내가 좋아요 누른 최근 300개의 게시물 가져오기 
	- tag에 대한 정보
- API 등록시 웹사이트 등록도 해야하고 제한적

- 1안) 
 	- 사용자가 좋아요 누른 게시물의 해시테그
 	- 해당 해시태그와 유사한 해시태그 사진 추천
	- 좋아요 정보는 웹에서 가져오기 여럽고 API사용이 제한적
- 2안)
	- 인기 해시태그로 검색하여 해시태그간의 유사도를 측정
	- 사용자로 부터 관심 해시태그 입력을 받는다
	- 관심 해시태그와 유사도가 높은 사진 제공  
	- 설문의 정확도가 떨어질 수도 있고 선호도 업데이트의 어려움
- 도구
	- data class
	- similarity class
	- recommender class
- 라이브러리
	- MovieLens(http://grouplens.org/datasets/movielens)
		- 10만개 / 1백만개 / 1천만개 별로 평가 데이터 셋 제공
	- SLF4J라이브러리(http://www.slf4j.org)
		- mahout라이브러리 호환성 필요
	- GUAVA라이브러리(https://code.google.com/p/guava-libraries)
	- apache commons Math 라이브러리(http://commons.apache.org/proper/commons-math)
		- 수치 계산용 라이브러리 
###데이터 셋
- 조회?
- 좋아요
- 댓글 유무
- 언급
- 해시태그
- 경향
	- 어떤 유저에 대해 좋아요를 누른다
	- 좋아요를 자주 누른다
	- 어떤 유저에 대해 댓글을 자주 작성한다
	- 댓글을 자주 작성한다
	- 조회만 한다
	- 
- training set / validation set / test set
	- 6 : 2 : 2

##boxplot
- input과 output사이의 연관 정도를 파악
##참고
http://sanghyukchun.github.io/73/
http://sanghyukchun.github.io/95/
http://bahnsville.tistory.com/894
http://wiki.gurubee.net/pages/viewpage.action?pageId=28118028
https://brunch.co.kr/@kakao-it/72



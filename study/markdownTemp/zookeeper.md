##Apache ZooKeeper##

###Waht is ZooKeeper?###
- 분산시스템 관리를 도와주는 시스템: 코디네이션 서비스(coordination service) 시스템
	- coordination data
		- status information
		- configuration
		- location information
		- etc
- 분산 작업을 제어하기 위한 트리 형태의 데이터 저장소
- providing distributed sysnchronization 분산 동기화
- fail over(장애 조치) / fail back(장애 복구)
- hadoop과 의존성이 없다
- 디스크에 영구 저장이지만, 빠른 처리를 위해 모든 트리 노드를 메모리에 올려놓고 처리한다. <br>
  즉, 대규모 데이터를 처리하기엔 무리, 메모리에 상태 이미지(트랜잭션 로그, 스냡샷)
- TCP연결
- synchronization primitives 제공
- fast, 특히 read연산이 빠르다

'''
###namespace
- file system과 유사
- / 를 이용한 구분
- 모든 노드는 주키퍼의 namespace 경로에 의해 식별된다
- file system과 다른점
	- 노드간에 연관된 data를 가질 수 있다 with children
- znode = data node
	- sta structure 유지 
		- version numbers for data changes
		- ACL(Access Controll List) changes and timestamps
	- sta structure를 통해 cache validations와 coordinated updates 
- Ephemeral node
	- 세션이 활성 상태에서 존재, 세션 종료시 삭제 
	- tbd(to be decided: 결정되어야 하는 것)를 구현하고자 할대 유용 
-Node
	- 영구 노드(Persistent Node) : 노드에 데이터를 저장하면 일부러 삭제하지 않는 이상 사제되지 않고 영구적으로 저장 
	- 임시 노드(Ephemeral Node) : 노드를 생성한 클라이언트의 세션이 연결되어 있을 경우만 유효
	- 순차 노드(Sequence Node) : 노드를 생성시 자동으로 sequence 번호가 붙는 노드, 주로 분산락을 구현하는데 이용
- 파일 시스템의 디렉토리 구조와 비슷
- CLI를 통해 탐색 가능	

###Watcher
- watcher 란
- znode와의 관계(+행동)
- 동작 방법
- 활용
	- SPOF(Single Point Of Failure) 처리
		- 액티브 서버 장애 발생시 스탠바이 서버가 액티브 서버로 동작  
	- lock기능 구현
'''
###주키퍼 앙상블 (Zookeeper Ensemble)
- ㅇㅇ
- 읽기 쓰기 과정
- Multi-Tenancy와 연관

###Guarantees
- 순자적인 일관성
- 원자성
- 단일 시스템 이미지
- 신뢰성
- 적시성(timeliness)

###simple CUI command
- 단순한 인터페이스 제공
- create
- delete
- exists
- get data
- set data
- get children
- sync
의미 추가

###활용
- 분산 서버 간의 정보 공유
- 서버 투입/제거 시 이벤트 처리
- 서버 모니터링
- 시스템 관리
- 분산 락 처리
- 장애 상황 판단 



replicated

strict odering

quorum : 어플리케이션에서 복제된 서버의 그룹을 quorum이라고 한다

##참고
recipe <http://zookeeper.apache.org/doc/trunk/recipes.html>
Zoopiter <http://d2.naver.com/helloworld/583580>
Zookeeper를 활용한 redis cluster 구성 <http://d2.naver.com/helloworld/294797>

<https://www.joinc.co.kr/w/man/12/zookeeper>
##질문
- 트리 전체가 주키퍼 서버들이고 어느 서버에 내가 입력한 데이터가 저장되는지는 모르는건가?




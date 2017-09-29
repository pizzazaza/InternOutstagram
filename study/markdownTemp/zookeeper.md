##Apache ZooKeeper##

###Waht is ZooKeeper?###
- 분산시스템 관리를 도와주는 애플리케이(coordination service)
- 시스템간 정보공유
- 클러스터에 있는 서버들의 상태 체크
- 분산된 서버들간에 동기화를 위한 락 처리
- configuration information 환경 정보 유지보수(구성관리)
- naming 이름정의
- providing distributed sysnchronization 분산 동기화
- 설정관리
- providing group service 
- 중앙 집중화된 서비스 이다.

###Zookeeper service
- servers : 서로 모두 인지
- 메모리에 상태 이미지(트랜잭션 로그, 스냡샷)
- Clents는 single Zookeeper server에 연결
- TCP연결
- synchronization primitives 제공
- fast, 특히 read연산이 빠르다

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

###Node
- Ephemeral Node : 노드를 생성한 클라이언트의 세션이 연결되어 있을 경우만 유효
- Persistent Node : 노드에 데이터를 저장하면 일부러 삭제하지 않는 이상 사제되지 않고 영구적으로 저장 
- Sequence Node : 노드를 생성시 자동으로 sequence 번호가 붙는 노드, 주로 분산락을 구현하는데 이용

###Watcher
- 클라이언트가 특정 znode에 watch를 걸어놓으면, 해당 znode가 변경이 되었을때, 클라이언트로 callback호출을 날려서 클라이언트에 해당 znode가 변경이 되었음을 알려준다. 그리고 해당 watcher는 삭제 된다.

###Conditional updates and watches
- watche의 개념으로 지원
- watche는 znode가 변경 될 때 트리거 되거나 삭제

###Guarantees
- 순자적인 일관성
- 원자성
- 단일 시스템 이미지
- 신뢰성
- 적시성(timeliness)

###simple API
- 단순한 인터페이스 제공
- create
- delete
- exists
- get data
- set data
- get children
- sync

###활용
- 디렉토리 형태의 데이타 저장소, 노드의 종류별 특성, watcher기능을 활용하여 다양한 시나리오에 사용
- Queue : watcher와 sequence node를 이용하여 구현, 큐 솔류션이 존재, 클러스터간 통신용 큐로 활용
- 서버 설정 정보 : 클러스터 내의 각 서버들의 설정 정보를 저장하는 저장소, watch를 이용해 변경 사항을 각 서버로 알려서 바로 반영 가능
- 클러스터 정보 : 현재 클러스터에서 기동중인 서버 목록을 유지
- 클로벌 락 : 분산 서버에서 공유 자원을 접근하려고 했을때, 해당 작업에 lock을 걸고 작업 수행하는 기능 구현 가능 


coddrdination data
- status information
- configuration
- location information
- etc


name space
znode - data node
- 데이터 레지스터로 구성, 파일 및 디렉토리와 유사
- 메모리에 데이터 보관, 처리량과 짧은 지연 시간

replicated

strict odering


##참고
https://www.joinc.co.kr/w/man/12/zookeeper
##질문
1. 하둡 설치 버전
2. 
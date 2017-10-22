
##Cluster

###Clustring
- 여러개의 시스템이 하나의 거대한 시스템으로 보이게 만드는 기술, 사용자에게 고가용성의 서비스 제공

- 디스크 공간 같은 하드웨어 자원을 공유할 수 있고, 여러 사용자에게 컴퓨팅 자원을 제공 가능

- 여러 컴퓨터간의 부하 조정, 가용성이 높은 시스템을 구죽, 주 시스템이 다운 되었을 때의 대비한 fail-over기능을 제공

###구성요소
- 노드, 관리자

- 클러스터 노드
	- 클러스터의 실질적인 작업을 처리

- 클러스터 관리자
	- 각 노드에 대한 자원분배 및 관리
	- 경우에 따라 노드가 관리자 기능을 수행하기도 한다.

###종류

- 공유 프로세싱 : HPC(High Performance Computing)
	- 리눅스 클러스터링
	- beowulf 프로젝
	- 하나의 작업을 여러 시스템 사이에 분배

- 부하조정 : Load Balancing 
	- 여러대의 웹서버 노두를 관리툴에서 부하를 분산
	- 노드 간 통신이 필요 없다
	- 모든 노드가 한꺼번에 동작
	- Load Balancing응 응용하여 Fail-Over 기능을 가지게 할 수 도 있다.

- Fail-Over
	- Load Balancing과 유사  
	- 평소에는 동작하지 않고 Primary서버가 문제 발생 시 백업 서버로써 가동

- 높은 가용성
	- 문제 발생시 최대한 가용성을 높일 수 있다.(clustring)
	- Fail-Over
	- 가용성 = 작동가능시간의 백분율
	
###작동방법[ Load Balancing 방식 ]
- 직접포워딩
 
- IP 터널링

- NAT

### Load Balancing scheduling algorithm
- Round-Robin scheduling
	- 상황을 생각하지 않고 라운드 로빈 방식으로	 
	- 동일한 서버 사양, 네트웍 상에서 단순하고 효율적 일수도

- Weighted Round-Robin Scheduling
	- 서버마다 다르게 가중치를 주어 요청 횟수를 다르게 준다
	- 요청에 대한 부하가 매우 많은 경우 서버 사이에 동적인 부하 불균형 발생 가능

- Least-Connection Scheduling
	- 가장 접속이 적은 서버로 요청을 직접 연결하는 방식
	- 동적인 스케줄링 알고리즘
	- 접속 부하가 큰 경우에도 아주 효과적으로 분산
	- 다양한 처리 용량을 지닌 서버로 구성되었을 경우 부하 분산이 효과적이지 못할 수 있다
		- 성능이 서로 다른 서버에 같은 양의 요청을 

- Weighted Least-Connection Scheduling
	- 가중치 + 최소 접속 스케줄

###공유 데이터 저장장치 및 시스템
- NFS(Network File System)

- AFS(Andrew File System)

- Coda

- Intermezzo

- GFS

- RAID
  
###장애 대응
- 어플리케이션과 서비스 장애
- 시스템과 하드웨어 장애 
- 여러기관의 사이트 장애 - 자연재해, 정전, 연결 중단 등으로 발생
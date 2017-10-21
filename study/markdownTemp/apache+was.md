#Apache + WAS 구축하기

- SOAP (Simple Object Access Protocol)
	- XML을 이용해서 분산처리 환경에서 정보교환을 쉽게 할 수 있도록 도와준다. 
	- XML은 플랫폼에 독립적으로 사용가능
	- RPC(Remote Procedure Call)
		- 별도의 원격 제어를 위한 코딩없이 다른 주소 공간에서 함수나 프로시저를 실행할 수 있게하는 프로세스 간 통신 기술
		- remote invocation 원격 호출
		- remote method invocation 원격 메소드 호출
- JAX-RS : 경량화된 REST 방식의 웹 어플리케이션 구현을 지원하는 자바 API
	- REST API랑 다른점 뭐가 좋은지 찾아보기
- forward proxy
	- target server의 정보를 proxy로 보내서 프록시가 데이터를 가져오는 방식
	- 인터넷 사용 제한, 감시(보안)
	- cacheing 으로 성능 향상
	- caching을 어떻게 제공? 결과는 내부서버에서 바로 가는거 같은데
	
- reverse proxy
	- client는 프록시로 요청하면 프록시가 배후(reverse)의 서버로부터 데이터를 가져오는 방식
	- 주로 load balancing을 위해 사용
	
	
	#zookeeper client example
	-zookeeper client 
		- zookeeper server와 connection을 유지
		- data를 모니터링




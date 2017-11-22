
# apache + tomcat 

## apache - httpd.conf
 - 전역 설정
	 - ServerRoot
	 - ServerTokens
	 - KeepAlive
	 - MaxKeepAliveRequests
	 - KeepAliveTimeout
	 - Listen
	 - LoadModule
		 - mod_jk를 이용
		 	- 톰캣 전용
		 	- JkMount 옵션을 이용하면 URL이나 컨텐츠별로 유연한 설정 가능
		 	- ex) image는 웹서버, 서블릿은 톰캣 
	 - Include
	 - User, Group 
	 	- 80번 포트를 사용하기 위해서는 root 권한이 필요
	 	- 아파치 프로세스가 루트 권한을 갖게 되므로 보안 이슈 발생
	 	- 사용자/그룹 apache/apache로 사용하고 로그인 불가능한 사용자로 만든다.
	 	- 현재 설정은 irteam:irteam으로 진행

	- 서버 설정
		- ServerAdmin
		- ServerName
		- AllowOverride
		- DocumentRoot
		- DirectoryIndex
		- <Directory> 지시자
			- Option과 권한 설정
			- Order allow, deny
				- allow먼저 권한확인 후 deny 확인
				- Allow from all ...
			- mod_status
				- web server 상태를 웹 기반으로 모니터링
				- 서버이름/server-status 형식의 URL로 호출 가능
				- 기본은 주석
				- <Location /server-status>
				- 누구나 접속 못하도록 권한 설정 중요 
		- \<Files> ~ \</Files>
			- 파일 이름에 따라 필요한 처리가 필요할 경우 사용
			- <File "파일명">
			- 파일명은 정규식 사용가능
		- Default Type
		- LogFormat
		- ErrorLog
		- CustomLog
		- Alias
		- AddDefaultCharSet

	- Virtual Host
		- 한장비에 여러 웹서비스 구동
		- IP 기반 가상호스트
		- PORT 기반 가상호스트
	
	 

	
## Tomcat 
- 아키텍처 
![Alt text](https://oss.navercorp.com/sejun-kim/intern/blob/master/study/image/fileserver/tomcat_architecture.png)
- HTTP 서버이며 서블릿/JSP의 컨테이너
- 커넥터 : 클라이언트와 통신 하는 모듈(HTTP, HTTPS, AJP)
- 구성요소
	- Server
	- Listener
	- Global Naming Resources
	- Service
		- 서버의 하위 컴포넌트
		- 하나 이상의 엔진을 구성요소로 가지고 엔진 하나당 이상의 커넥터를 연결 
	- Connectors
	- Engine
		- 특정 서비스에 대한 요청 처리 파이프 라인
		- 커넥터의 요청을 받아 처리후 커넥터로 전달
	- Hosts
		- virtualHost를 의미 
	- Context
		- 웹 어플리케이션
		- 하나의 호스트는 여러 개의 웹 어플리케이션을 가진다
	- Valve 

## web 서버와 WAS 연동
- 연동 이유
	- web 서버가 정적 컨텐츠 제공하는데 더 성능이 뛰어남
	- 유연한 클러스터링
	- 모듈 기반의 확장성
	- 가상 호스트
	- 보안
	![Alt text](https://oss.navercorp.com/sejun-kim/intern/tree/master/study/image/fileserver/webserver_was_dist.png)
	- mod_jk
		- URL, 컨텐츠별 유연한 매핑 가능
		- AJP 커넥터 (전용 바이너리 프로토콜), 빠른 속도
	- mod_proxy
		- HTTP리버스 프록시로 동작
		- WAS에 독립적 


	
### AJP protocol
- 웹서버와 서블릿 컨테이너 사이의 통신에 연관된 목적을 포함하는 mod_jserv와 ajp12를 확장하는 것
- 속도향상(바이너리 포맷), ssl을 지원
- 요청에 대해 reconnection시간을 피하기 위해 영구적인 커넥션을 사용하는 것
- 스트림 사이즈를 줄이기 위해 많은 http커맨드 포함
- 패킷 지향적 


### etc
- SOAP (Simple Object Access Protocol)
	- XML을 이용해서 분산처리 환경에서 정보교환을 쉽게 할 수 있도록 도와준다. 
	- XML은 플랫폼에 독립적으로 사용가능
	- RPC(Remote Procedure Call)
		- 별도의 원격 제어를 위한 코딩없이 다른 주소 공간에서 함수나 프로시저를 실행할 수 있게하는 프로세스 간 통신 기술
		- remote invocation 원격 호출
		- remote method invocation 원격 메소드 호출
		
- JAX-RS : 경량화된 REST 방식의 웹 어플리케이션 구현을 지원하는 자바 API
	
- forward proxy
	- target server의 정보를 proxy로 보내서 프록시가 데이터를 가져오는 방식
	- 인터넷 사용 제한, 감시(보안)
		
- reverse proxy
	- client는 프록시로 요청하면 프록시가 배후(reverse)의 서버로부터 데이터를 가져오는 방식
	- 주로 load balancing을 위해 사용
	- 정적파일 caching 
	![Alt text](https://oss.navercorp.com/sejun-kim/intern/blob/master/study/image/fileserver/reverse_proxy.PNG)
	
## zookeeper client example
- zookeeper client application
	- zookeeper server와 connection을 유지
	- data를 모니터링

## 참고자료
- [AJP](http://jjosh.tistory.com/entry/ajp13-protocol)
- [apache web server](https://www.lesstif.com/pages/viewpage.action?pageId=18219482)
- [tomcat WAS](https://www.lesstif.com/pages/viewpage.action?pageId=18219510)




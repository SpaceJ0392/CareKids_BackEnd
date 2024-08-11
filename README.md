# AI를 활용한 육아 지원 종합 플랫폼

![](https://github.com/user-attachments/assets/3947764a-d9c8-4187-a4e3-5c03e52165fd)

AI를 활용한 육아 지원 종합 플랫폼 CareKids입니다!  
기존의 분산되어 있던 육아 정보를 통합하여 사용자의 편의성과 공공성을 확보하기 위한 목적의 플랫폼입니다. 🙂

## 목차
- 프로젝트 개요
- 주요 기능소개
- 담당부분 상세설명
- 시연영상

## 프로젝트 개요
- **프로젝트 이름** : AI를 활용한 육아 지원 종합 플랫폼 CareKids
- **프로젝트 기간** : 2024-06-28 ~ 08.02 (6주)
- **개발 도구** :
  - **Front-End** : React, HTML, CSS, JavaScript
  - **Back-End** : Spring boot, JPA, QueryDSL, Spring Security
  - **DB** : H2 (개발 DB), MySQL (운영 DB), Chroma (벡터 DB)
  - **AI** : Python, HuggingFace, Sklearn, OpenAI, LangChain
  - **협업 툴** : Notion, Git, Discord

 - **프로젝트 아키텍처** :
     ![프로젝트 아키텍처](https://github.com/user-attachments/assets/ae9fc9ae-c2b3-4f22-83a7-102fbd0c96a9)

## 주요 기능 소개
  - **AI 추천 시스템을 통한 아이와 갈만한 장소 추천 서비스**

    <img src="https://github.com/user-attachments/assets/449bf4eb-e429-4310-8fb6-da3645cb9887" width="450" height="300" style="text-align:center" />  

    [전국 가족 유아 동반 가능 문화시설 위치 데이터](https://www.data.go.kr/data/41850466/linkedData.do) 활용,
    서울시의 아이가 갈만한 장소 데이터를 토큰화 후, 임베딩하여 만든 벡터 DB를 기반으로 입력된 지역구, 쿼리를 임배딩하여 벡터 유사도 측정 방식으로 구현한
    장소 추천 서비스
    
  - **사용자 자녀 연령대 기반 놀이 정보 제공 및 AI 챗봇을 통한 추천 서비스**

    <img src="https://github.com/user-attachments/assets/8f06bc4d-4f6b-40f7-9609-7b6a45d94e7c" width="450" height="300" style="text-align:center" />

    연령대 구간별 놀이 정보 제공 및 LangChain을 활용한 RAG GPT 챗봇으로 아이와 할만한 놀이 추천!

  - **긴급 진료 기관 및 주말 어린이집 정보 제공 서비스**

    <img src="https://github.com/user-attachments/assets/5d04cf26-0bf4-4af2-94ad-ecf4e6ee5247" width="450" height="300" style="text-align:center" />

    야간 시간, 주말에 아이를 치료하기 위한 서울 내 긴급 진료 기관 정보 제공
    바쁜 부모님들을 위한 서울시 지원 주말 어린이집 정보 제공

  - **서울시 지역구별 육아 정책 정보 제공 서비스**

    <img src="https://github.com/user-attachments/assets/324e6051-890d-4b8d-a4bc-d36cfa608628" width="450" height="300" style="text-align:center" />

    [몽땅정보 만능키](https://umppa.seoul.go.kr/hmpg/chpo/chre/bzmg/bzmgList.do)를 통한 서울시 자체 혹은 거주 지역구별 서로 다른 육아 지원 정책 정보 제공

## 담당부분 상세설명

  - **담당** : 벡엔드 개발 및 벡엔드 리더
  - **구현 상세 설명**
    - **메인 API** : 로그인 상황에 따른 사용자별 최적화 놀이 정보, 육아 정책 제공 (QueryDSL 동적 쿼리 구현)
    - **회원가입 API** : jarkarta.validation 및 자체 validation 메소드를 통한 유효성 검사, 이메일 인증 구현 및 Spring Security를 통한 비밀번호 암호화 (Secure Coding)
    - **로그인 API** : 일반 로그인 및 Oauth2를 통한 Google, Naver 소셜 로그인 구현, 관리자 페이지를 위한 관리자 로그인 로직 구현
    - **긴급 진료 기관, 주말어린이집, 육아 정책 API** : QueryDSL를 이용한 N + 1 문제 해결 및 페이지네이션, 동적 쿼리를 통한 지역별, 사용자 자녀 연령대별 필터링 기능 구현
    - **관리자 페이지 API** : 관리자 인가 정책 적용 및 장소, 진료기관, 어린이집, 놀이, 육아 정책, 공지사항, 문의사항 목록 조회 및 등록, 수정, softDelete를 통한 삭제 구현

## 시연영상

- [youtube - 시연영상](https://youtu.be/6G9NVoPW2Dc)

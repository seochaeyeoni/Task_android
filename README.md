# Introduction to Android development

<br>안드로이드로 개발을 처음 접할 때, 공부한 것들을 정리한 글입니다. 소프트스퀘어드512에서 진행한 과제에 덧붙여서 **추가학습**한 내용들을 위주로 정리하였습니다. 
주로 안드로이드 개발자 문서의 내용을 바탕으로 하였고, 이외에 제가 공부하면서 도움이 되었던 강의나 유튜브 채널 등은 하단에 적어두었습니다. 
이 글이 안드로이드 개발을 처음 접하는 모든 분께 도움이 되었으면 좋겠습니다✨</br>
  
---


## Android Studio Structure

 안드로이드 스튜디오를 깔고, 가장 처음 마주하게 되는 프로젝트 구조!

```
Android
📂app                        //하나의 모듈
 ┣ 📂manifests               //AndroidManifest.xml 파일이 위치함.
 ┣ 📂java                    //관행적으로 폴더이름은 java
 ┃ ┣ 📂com.example.app       //java 혹은 kotlin 파일들이 위치함.
 ┃ ┣ 📂com.example.app(androidTest) //testcode 작성
 ┃ ┗ 📂com.example.app(test)        //testcode 작성
 ┗ 📂res                     //resource 관련 파일들이 위치함.    
   ┣ 📂drawable              //image, 
   ┣ 📂layout                //ui 컴포넌트들. ex) activity_main.xml
   ┣ 📂mipmap                //런처 아이콘(크기별)
   ┗ 📂values                //colors, strings, themes
🐘Gradle Scripts
 ┣ 📄 build.gradle(project)  //모든 모듈에 적용되는 빌드 구성을 정의
 ┗ 📄 build.gradle(module)   //각 모듈에 대한 빌드 구성을 정의(ex.라이브러리 dependency)
   ...이하 생략
```

## Manifest

앱이 시작하기 전에 가장 먼저 보는 파일 : `AndroidManifest.xml`

👀 왜 가장 먼저 보지? 앱에 관한 필수 정보를 설명하는 **'명세서'**

📝 매니페스트 파일에서 선언하는 것들

- 앱의 패키지 **이름**(ex: com.example.myapp)
- 앱의 **컴포넌트**(Activity, Service, Broadcast Receiver, Contents Provider) 그리고 인텐트 필터와 같은 기능도 선언 가능

```markup
ex) 하나의 액티비티는, 앱이 실행되었을 때 실행될 액티비티(앱의 진입점!)라는 것을 인텐트 필터로 선언해주어야 함.
<activity android:name=".MainActivity">
      <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
</activity>
```

- 앱이 시스템 또는 다른 앱의 보호된 부분에 액세스하기 위해 필요한 **권한**
- 앱에 **필요한** 하드웨어 및 소프트웨어 **기능**

즉, Manifest를 보면, 이름, 구성 컴포넌트, 필요한 권한(플레이스토어 앱 정보에도 표시되는 그 권한!)과 기능을 알 수 있으므로 어떤 프로젝트인지 대강 어림 짐작할 수 있다! 

## Application Fundamentals

`안드로이드 4대 컴포넌트`로 알려져 있다. 이 컴포넌트들은, 위에서 설명한 Manifest 파일에 필수적으로 선언되어야 한다. 각각은 시스템 또는 사용자가 앱에 들어갈 수 있는 진입점이다. 

- Activity - 하나의 앱에 최소 하나 존재. 사용자와 상호작용 담당.
- Service - UI가 없는 백그라운드 작업(ex. 네트워크 트랜잭션 처리, 음악 재생 등). 액티비티와 같이 메인 스레드에서 동작. (+ 최신 API 레벨에서는, 백그라운드 실행 제한이 있다. 이에 백그라운드 작업과 관련한 API를 개발자 문서에서 '백그라운드 처리 가이드'로 제공한다.)
- Broadcast Receiver - 대부분 시스템으로부터 발생(ex. 배터리 부족, 사진 캡쳐 알림). 이러한 디바이스 상황에 대한 적절한 작업 처리.
- Contents Provider - 데이터 관리. 다른 앱에 데이터 제공. 다른 앱의 데이터 변경.

## Intent

- 액티비티, 서비스, 브로드캐스트 리시버는 '인텐트'라는 비동기식 메시지로 활성화됨.
- 액티비티 시작, 서비스 시작, 브로드캐스트 전달 등에 사용됨.
- 명시적 인텐트 : 클래스 이름 정확히 제공. (ex. 액티비티 시작, 서비스 시작)
- 암시적 인텐트 : 이름x 수행할 일반적인 작업 선언. 안드로이드 시스템이 모든 앱에서 해당 인텐트와 일치하는 인텐트 필터 검색 → 찾으면, 인텐트 넘겨서 startActivity! (ex. 앱 내 데이터 공유하는 경우, 해당 인텐트를 허용하는 앱들을 선택할 수 있게 창이 뜬다)

<br></br>

---

## Layout

- ConstraintLayout

 depth가 깊어지지 않게, 복잡한 ui들을 구현할 수 있음. 기본적으로 xml 파일을 만들면, 컨스트레이아웃으로 빌드됨. 부모와 자식뷰 사이에 chain을 거는 방식으로,  반응형 ui를 만들기 좋음.

🐣 이런 것들을 할 수 있음! ( 참고 : <a href="https://www.youtube.com/watch?v=RkqMFLrpaT8" target="_blank">DroidKnights 2018 안세원 지금은 Constraint Layout 시대</a> )

0dp = match_constraints (match_parent x)

Percent size (부모 뷰의 크기 - 패딩에 비례하여 뷰의 크기 결정), Dimension Ratio, bias(비율로 위치 지정), guideline(특정 위치를 기준점으로 삼을 때), chain style(spread, spread inside, packed, weighted), barrier, Group(여러 뷰의 visibility를 한 번에 조정할 때 유용), ConstraintSet, PlaceHolder(기존 뷰의 위치를 재조정)

- LinearLayout

 가장 직관적인 레이아웃. 복잡하지 않은 ui의 경우, 리니어레이아웃으로도 충분히 depth를 깊게 하지 않고, 구현 가능.

- <a href="https://github.com/seochaeyeoni/Task_android/tree/master/Task01/app/src/main/res/layout" target="_blank">처음 만든 xml 파일</a>
- 거의 모든 레이아웃을 활용하여 만든 <a href="https://github.com/seochaeyeoni/Task_android/blob/master/Task01/app/task01_supplement/src/main/res/layout/activity_main_supplement.xml" target="_blank">오늘의 집 메인 xml 파일</a>
- 위의 두 뷰그룹을 포함한, 많은 안드로이드 뷰&뷰그룹을 설명하는 유튜브 영상 (추천) → <a href="https://www.youtube.com/playlist?list=PLMocbRXgGcjZMKFygjNXccaY_aQ91Xgfo" target="_blank">코딩발전소 안드로이드 뷰&뷰그룹</a>
- 1년 동안의 개인적인 경험 상, ConstraintLayout 90%, LinearLayout 10%로 개발. 두 개만 제대로 알아두면 뚝딱뚝딱 뷰공장 가능.
- 🎁 **Jetpack Compose**(Declarative UI) 라는 새로운 방식의 UI 구현 방법도 있다!


<br></br>

---

## Life Cycle

 앱이 실행되고, 중단되는 등의 이벤트가 발생할 때, 생명주기 함수가 자동적으로 호출된다. 따라서 생명주기 함수들을 적절히 override 해서 해당 생명주기에 알맞는 코드를 작성해야, 좋은 사용자 경험을 제공할 수 있다.

**Activity Life Cycle**

- `onCreate()` - 액티비티를 생성할 때 실행. 필수 구현. 전체 수명 주기 동안 **한 번만 발생해야 하는** 로직을 실행.
- `onStart()` - 액티비티가 사용자에게 표시됨(상호작용x). 앱이 ui를 관리하는 코드를 초기화. onStop()과 대칭.
- `onResume()` - 앱이 사용자와 **상호작용**. 앱이 포커스를 잃기 전까지 이 상태. onPause()와 대칭.
- `onPause()` - 사용자가 액티비티를 떠나고 있는 중에 호출 or 다른 화면으로 인해 포커스를 잃었을 때 호출(여전히 일부분 보일 수 O)
- `onStop()` - 사용자가 액티비티를 완전히 떠나서, 더 이상 화면에 보이지 않을 때 호출. 부하가 큰 종료 작업 실행(ex. db에 데이터 저장).
- `onDestroy()` - 액티비티가 소멸되기 전에 호출


<a href="https://github.com/seochaeyeoni/Task_android/tree/master/Task02/app/musicplayer_ex" target="_blank">[액티비티 생명주기를 활용하여 만든 앱(playlist playlist - 플레이리스트 재생 앱)</a>


<br></br>

---

## ListView

 리스트형 UI를 위한 뷰. layout xml에서 ListView를 만들고, 각각의 아이템으로 들어갈 item xml을 만들고, 표시할 데이터리스트를 만들고(+ 데이터 클래스), 이를 연결할 어댑터를 만든다. **아이템을 어댑터한테 주고(getView - LayoutInflater), 데이터를 어댑터한테 주고(ArrayList<Data>), 어댑터를 리스트뷰에 준다(setAdapter).** getView()에서 아이템뷰를 만들어서 달아주고 화면에서 안 보이면 삭제하는 방식으로, 아이템이 많아질 경우 성능 저하가 있다(따라서 잘 안 씀). 그래서 나타난 RecyclerView!🙌

## RecyclerView

 리스트형 UI를 위한 뷰. 아이템뷰를 재활용하기 때문에 recycler 라는 이름이 붙여졌다. 뷰 객체가 화면에서 사라지면, 이를 재사용하고, 데이터만 새로 갱신한다. 따라서 화면에 보일 정도(+여유분)로만 뷰 객체를 만들어서 쓰는 방식이므로, 데이터리스트가 길어질수록, **리스트뷰보다 훨씬 성능이 좋다. 현재 ListView는 거의 쓰이지 않고, RecyclerView를 쓴다.** 하지만 사용방법은 비슷하다(리스트뷰의 getView에서 하던 일이 나누어졌고, ViewHolder라는 재사용할 뷰를 가지고 있는 객체가 있을 뿐).

✔ 어댑터에 구현되는 메서드

- `onCreateViewHolder()` - ViewHolder와 그에 연결된 View를 생성하고 초기화한다. (재사용할) 껍데기만 만들어 놓음!
- `onBindViewHolder()` - ViewHolder에 데이터를 bind 해 준다. 껍데기에 데이터가 채워진 상태!
- `getItemCount()` - 가장 먼저 데이터 세트 크기를 가져온다.

<a href="https://github.com/seochaeyeoni/Task_android/tree/master/Task03" target="_blank">ListView, RecyclerView 를 이용하여 만든 앱(김과외 클론)</a>

<br></br>

---

## Multi Thread

프로세스 : 프로그램이 실행된 것

싱글 스레드 : 한 프로세스 내의, 하나의 실행 단위

멀티 스레드 : 한 프로세스 내의, 둘 이상의 실행단위

멀티 스레딩 : 멀티 스레드로 수행하는 방식(컨텍스트 스위칭 - 동시성)

❗ 안드로이드에서 스레드를 사용할 때 알아야 할 점

- UI를 그려주는 것은 오직 **Main UI Thread**에서만 할 수 있다! (동기화 문제로 인해 안드로이드에서 Lock 걸어둠!)
- 다른 스레드에서 UI를 변경하고 싶으면 자바에서는 **Handler**, 코틀린에서는 **runOnUiThread**를 이용하면 된다(+ 코틀린에는 `Coroutine`이 있다 - 코루틴은 스레드 위에서 실행 되므로, 코루틴을 여러 개 실행해도 컨텍스트 스위칭이 발생하지 않아 성능상 더 좋다).
- Main UI Thread에서 오래 걸리는 작업을 해서 멈춘 화면을 보고 있어야 하면 **ANR**(Android Not Responding)이 발생한다. 따라서 오래 걸리는 작업(ex. 네트워크 통신)은 다른 스레드에서 해당 작업을 수행하여야 한다.

[Thread를 이용하여 만든 앱(스폰지밥 수학게임)](https://github.com/seochaeyeoni/Task_android/tree/master/Task04/app/mathgame_spongebob)

---

## Network Intro & API

---

## Token & Restful

---


추천 강의

<a href="https://www.boostcourse.org/mo316" target="_blank">부스트코스 안드로이드 앱 프로그래밍</a>

추천 유튜브

센치한 개발자 

홍드로이드 

코딩발전소

// Firebase SDK 스크립트를 import합니다. 버전은 HTML 파일과 맞춰주세요.
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

// =======================================================================
// !!중요!! HTML 파일에 있던 Firebase 프로젝트 설정 값을 그대로 복사해서 붙여넣으세요.
// =======================================================================
const firebaseConfig = {
    apiKey: "",
    authDomain: "",
    projectId: "",
    storageBucket: "",
    messagingSenderId: "",
    appId: "",
    measurementId: ""
};
// =======================================================================

// Firebase 앱 초기화
firebase.initializeApp(firebaseConfig);

// 메시징 서비스 워커를 가져옵니다.
const messaging = firebase.messaging();

// (선택 사항) 백그라운드에서 메시지를 처리하는 핸들러입니다.
// 지금은 토큰 발급이 목적이므로 이 부분은 그대로 두셔도 됩니다.
messaging.onBackgroundMessage((payload) => {
    console.log(
        '[firebase-messaging-sw.js] Received background message ',
        payload
    );
});
const stompClient = null;
const sessionId = null;

function connect (){
    const socket = new sockJS('/chat-websocket');
    stompClient = Stomp.over(Socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/user/queue/messages', onMessageRecieved);

    // Send join Message
    const chatMessage = {
        senderId : 'user-'+Date.now(),
        senderName : 'Customer', 
        type : 'JOIN', 
        message : 'Customer joined' 
    };
}

function onError(error) {
    console.error('WebSocket error:', error);
}

function sendMessage() {
    const messageContent =document.getElementById('messageImput').value.trim();

    if(messageContent && stompClient){
        const chatMessage = {
            senderId : 'user-' + Date.now(),
            senderName : 'customer', 
            message : 'messageContent',
            type : 'CHAT',
            receiverId : 'admin'
        };

        stompClient.send("app/chat.sendMessage", {}, JSON.stringify(chatMessage));

        displayMessage(messageContent, 'user');
        document.getElementById('messageInput').value = '';
    }   
}

function onMessageRecieved(payload) {
    const message = JSON.parse(payload.body);
    if (message.senderID ==='admin' || message.senderName === 'Support Admin') {
        displayMessage (message.message, 'admin');
    }
}

function escapeHtml(text) {
    const div = document.getElementById('div');
    div.textContent = text;
    return div.innerHTML;
}

function toggle() {
    const widget = document.getElementById('chatWidget');

    if(widget.style.display === "none") {
        widget.style.display ="block";
    } else {
        widget.style.display ="none";
    }
    widget.classList.toggle('active');
}

function handleKetPress(event) {
    if (event.key === ' Enter') {
        sendMessage();
       }
}

//initalize chat 
connect();
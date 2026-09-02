let stompClient = null;
let sessionId = null;
let token = localStorage.getItem('jwt');
let oldMessageSubscription =null;

async function connect() {
        const socket = new SockJS('/chat-websocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({
            'Authorization' : token
        }, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/user/queue/messages', onMessageRecieved);


    // Send join Message
    const chatMessage = {
        senderId : "user_" + Date.now(),
        senderName : 'Customer', 
        type : 'JOIN', 
        message : 'Customer joined',
        receiverId : 'admin'
    };
    stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));

    setTimeout(() =>  {
        if(!oldMessageSubscription){
                    fetchOldMessages();
        }
    },1000);
}

function onError(error) {
    console.error('WebSocket error:', error);
}

function sendMessage() {
    const messageContent =document.getElementById('messageInput').value.trim();


    if(messageContent && stompClient){
        const chatMessage = {
            senderId : 'user-' + Date.now(),
            senderName : 'Customer',
            message : messageContent,
            type : 'CHAT',
            receiverId : 'admin'
        };

        stompClient.send("/app/chat.SendMessage", {}, JSON.stringify(chatMessage));
        displayMessage(messageContent, 'customer');
        document.getElementById('messageInput').value = '';
    }

}

function fetchOldMessages(){
   oldMessageSubscription = stompClient.subscribe("/user/queue/oldMessages", function(message) {
                     console.log("Raw Body", message.body);
                     const oldMessages = JSON.parse(message.body);

                     if(oldMessages) {
                        console.log("Old messages exist...");
                        oldMessages.forEach(msg => {
                                                 if(msg.type === "JOIN"){
                                                 }else if(msg.senderId === "admin"){
                                                     displayMessage(msg.message, msg.senderId);
                                                 }else{
                                                    displayMessage(msg.message, "customer")
                                                 }
                                                 });
                     }else {
                        console.log("No Old messages to appear");
                     }
                 })

    try{
        stompClient.send("/app/chat.loadOldChat", {}, JSON.stringify({}));
    }catch(error) {
        console.error("Error loading Old Messages :", error);
    }
}


function onMessageRecieved(payload) {
    const message = JSON.parse(payload.body);
    if (message.senderID ==='admin' || message.senderName === 'Support Admin') {
        displayMessage (message.message, 'admin');
    }
}

function displayMessage(message, sender) {
            const messagesDiv = document.getElementById('chatMessages');
            const messageElement = document.createElement('div');
            messageElement.className = `${sender}-message`;
            messageElement.innerHTML = `${escapeHtml(message)}`;
            messagesDiv.appendChild(messageElement);
            messagesDiv.scrollTop = messagesDiv.scrollHeight;
        }

function escapeHtml(text) {
    const div = document.createElement('div');
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

function handleKeyPress(event) {
    if (event.key === 'Enter') {
        sendMessage();
       }
}

//initialize chat
if(token) {
    connect();
}else {
    const loginBeforeMsg = document.getElementById("loginbeforeMsg");
    loginBeforeMsg.style.display = "flex";
}
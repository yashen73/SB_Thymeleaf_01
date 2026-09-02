function toggle() {
    const widget = document.getElementById('chatWidget');

    if(widget.style.display === "none") {
        widget.style.display ="block";
    } else {
        widget.style.display ="none";
    }
    widget.classList.toggle('active');
}

let stompClient = null;
let currentChatsession = null;

//Admin information
const adminId = 'admin';
const adminName = 'support Admin';

//sample active chats(in prodcution, fetch form backend)
let activechats = [
    {sessionId: 'session1', userId: 'user1', userName: 'John Doe', lastMessage: 'Hello!', unread: 2 },
    {sessionId: 'session2', userId: 'user2', userName: 'Jane Smith', lastMessage: 'Hi!', unread: 3 }
];

function connect () {
    const socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function() {
        console.log('Admin connected Successfully');
        stompClient.subscribe('/user/queue/messages', onMessageReceived);
        loadActiveChats();
    }, onError)
}

function onError(){
    console.error('Connection error:', error);
    setTimeout(connect, 5000);
}

function loadActiveChats() {
    const chatlist = document.getElementById('chatlist');
    chatlist.innerHTML =" ";

    activechats.forEach(chat => {
        let oneChatNameElement = createChatUserElement(chat);
        chatlist.appendChild(oneChatNameElement);
    });
}

function createChatUserElement(chat) {
    let oneChatNameElement = document.createElement('div');
    oneChatNameElement.className = 'select-chat-person';
    oneChatNameElement.onclick = () => selectChat(chat);
    oneChatNameElement.innerHTML = `
        <div class="name-of-chatter">
            ${chat.userName}
        </div>
        <div class="id-of-chatter">
                    ${chat.userId}
        </div>
        <span class="count-of-messages">
            ${chat.unread}
        </span>
    `;
    return oneChatNameElement;
}

function selectChat(chat) {
    currentChatsession = chat;
    console.log("current Chat session is with", currentChatsession.userName);

    //Updating UI
    document.querySelectorAll('.select-chat-person').forEach(el => el.classList.remove('active'));
    event.currentTarget.classList.add('active');

    document.getElementById('chatHeaderName').innerHTML = currentChatsession.userName;

    loadChatHistory(chat.sessionId);
}

function loadChatHistory(sessionId) {
    fetch(`/chat/${sessionId}`)
    .then(response => response.json())
    .then(messages => {
        const messageDiv = document.getElementById('chatMessages');
        messageDiv.innerHTML = '';

        messages.forEach(message => {``
            displayMessage(message.message, message.senderId === 'admin' ? 'admin' :'user');
        });

        messageDiv.scrollTop = messageDiv.scrollHeight;
    });
}

function sendAdminMessage() {
    const messageContent = document.getElementById('adminMessageInput').value.trim();
    if( messageContent && currentChatsession && stompClient) {
        const message = {
            senderId : 1,
            senderName: 'yash',
            receiverId: currentChatsession.userId,
            message: messageContent,
            type: 'CHAT',
            sessionId: currentChatsession.sessionId
        };

        stompClient.send("/app/chat.SendMessage", {}, JSON.stringify(message));

        displayMessage(messageContent, 'admin');
        document.getElementById('adminMessageInput').value = '';
    }
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    if (message.senderID !== 'admin' && currentChatsession && message.sessionId === currentChatsession.sessionId) {
        displayMessage(message.message, 'user');
        markMessageAsRead(message.id);
    } else if(message.senderID !== 'admin') {
        updateUnreadCount(message.sessionId);
    }
}


function displayMessage(message, sender) {
    const messageDiv = document.getElementById('chatMessages');
    const messageelement = document.createElement('div');
    messageelement.className = `${sender}-message-content`;
    messageelement.innerHTML = `
            ${escapeHTML(message)}
            <span class="muted-text">
                ${new Date().toLocaleTimeString()}
            </span>
        `;
    messageDiv.appendChild(messageelement);
    messageDiv.scrollTop = messageDiv.scrollHeight;
}


function markMessageAsRead(messageId) {
    fetch('/admin/message/read/${messageId}', { method: 'POST'});
}

function updateUnreadCount(sessionId) {
    const caht  = activechats.find(c => c.sessionId === sessionId);
    if(chat) {
        chat.unread = (chat.unread || 0) +1;
        loadActiveChats();
    }
}

function handleAdminKeyPress(event) {
    if (event.key === 'Enter') {
        sendAdminMessage();
    }
}

function escapeHTML(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

connect();

setInterval(() => {

    fetch('/admin/active-chats')
    .then(response => response.json())
    .then(chats => {
        activeChats = chats;
        loadActiveChats();
    });
}, 20000);
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
let token = null;
const chatlist = document.getElementById('chatlist');

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

    stompClient.connect({
        'Authorization' : token
    }, function() {
        console.log('Admin connected Successfully');
        stompClient.subscribe('/user/queue/messages', onMessageReceived);
        loadChatHeaders();
        //loadActiveChats();
    }, onError)
}

function onError(){
    console.error('Connection error:Z', error);
    setTimeout(connect, 5000);
}

function loadActiveChats(username, userId, unreadmsgcount) {


        let oneChatNameElement = createChatUserElement(username, userId, unreadmsgcount);
        chatlist.appendChild(oneChatNameElement);
}

function createChatUserElement(username, userId, unreadmsgcount) {

    let oneChatNameElement = document.createElement('div');
    oneChatNameElement.className = 'select-chat-person';
    oneChatNameElement.onclick = () => selectChat(username, userId);
    oneChatNameElement.innerHTML = `
        <div class="name-of-chatter">
            ${username}
        </div>
        <div class="id-of-chatter">
                    ${userId}
        </div>
        <span class="count-of-messages">
            ${unreadmsgcount}
        </span>
    `;
    return oneChatNameElement;
}
//triggers from FrontEnd
function selectChat(username, userId) {
    currentChatSessionUserName = username;
    console.log("current Chat session is with", currentChatSessionUserName);

    //Updating UI
    document.querySelectorAll('.select-chat-person').forEach(el => el.classList.remove('active'));
    event.currentTarget.classList.add('active');

    document.getElementById('chatHeaderName').innerHTML = currentChatSessionUserName;

    loadChatHistory(userId);
}

function loadChatHeaders() {
    /*
    try {
        let chatHeaders = stompClient.send("/adminChat/chat.loadChatHeader", {}, JSON.stringify());
        if(charHeaders) {
            chatHeaders.forEach(message =>
            )
        }
    }catch (error) {
        console.error("can not fetch for Chat Headers : ", error);
    }*/

    try {
        fetch(`/adminChat/chat/loadChatHeader`, {
                method : 'GET',
                headers : {
                    'Authorization' : token
                },
            })
            .then(response => response.json())
            .then(chatheaders => {
                chatlist.innerHTML =" ";
                let checkedSenderIdArray = [];
                chatheaders.forEach(chatheader => {

                    if(!checkedSenderIdArray.includes(chatheader.senderId) && chatheader.type === 'JOIN'){

                    }else if(!checkedSenderIdArray.includes(chatheader.senderId) && chatheader.status === 'DELIVERED' && chatheader.senderId !== 'admin'){

                        let temp = chatheader.senderId; //Catching Sneder Id to catch number of unread msgs
                        let countOfUnreadMessages = 0;  //Bucket to get count of unread messages

                        //circulating object list to catch DELIVERED AND match SENDERID..
                        chatheaders.forEach(cochatheader => {
                            //Comparing SENDERID and msg STATUS...
                            if(cochatheader.senderId === temp && cochatheader.status === 'DELIVERED'){
                                countOfUnreadMessages++;
                            }
                        });

                        //Creating Chat header.....
                        loadActiveChats(chatheader.senderName, temp, countOfUnreadMessages);
                        console.log("passing loadActiveChats : ", chatheader.senderName, temp, countOfUnreadMessages);
                    }
                    checkedSenderIdArray.push(chatheader.senderId); //Including to the checked list
                });
            })
    }catch (error) {
        console.error("can not fetch headers : ", error);
    }
}


function loadChatHistory(userId) {
    fetch(`/chat/${userId}`)
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
    fetch('/adminChat/message/read/${messageId}', { method: 'POST'});
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

function getToken() {
    const cookies = document.cookie.split("; ");
    for (let cookie of cookies) {
        const[name, value] = cookie.trim().split("=");

        if (name === 'jwt') {
            return decodeURIComponent(value);
        }
    }
    return null;
}

try {
    token = getToken();
    try{
        connect()
    }
    catch(error) {
        console.error("Error connecting on Chat", error);
    }
}catch(error)  {
    console.error("No token found", error);
}


setInterval(() => {

    fetch('/admin/active-chats')
    .then(response => response.json())
    .then(chats => {
        activeChats = chats;
        loadChatHeaders();
    });
}, 5000);
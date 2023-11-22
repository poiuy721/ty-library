<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>

<html>
<head>
    <title>QR �ڵ� ���� �� ����</title>
    <!-- qr�ڵ� ���̺귯�� cdn -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<!-- qr�ڵ� �׷����� div  -->	
    <div id="qrcode" style="display: block"></div>
    <!-- id�տ� �κ� url �ڵ����� ��Ī�Ǽ� �� -->
    <input type="text" id="url-front" style="width: 350px ; display: none" >
    <!-- qr�� ���� id �κ� -->
    <input type="text" id="text-input" placeholder="QR �ڵ忡 ���� Id�� �Է��ϼ���"  style="width: 150px"><br>
	<!-- url �ϼ� -->
    <input type="text" id="url-full" placeholder="��ü url" style="width: 400px" readonly>
    <!-- ��ư Ŭ���� qr�ڵ� ���� -->
    <button id="generate-button">QR �ڵ� ����</button><br>
    <!-- �ڵ����� id�� ���ϸ� �������� �������� ���ð��� -->
    <input type="text" id="file-name" placeholder="QR�ڵ� ���� �̸��� �Է��ϼ���"> 
    <!-- �ٿ�ε�� ������ ���� ���ϸ����� ���� �ٿ�ε�� -->   
    <button id="save-button">QR �ڵ� ����</button>
    <!-- ��񿡼� books���� id���� ������ �� -->
    <button id="get-ids">id��������</button>
    <!-- ������ �� id��� qr����� ������ -->
	<button id="auto-gen">�ڵ�����</button>
    <script type="text/javascript">
    	// ������Ʈ ������ �Ҵ�
        const qrcodeDiv = document.getElementById("qrcode");
        const urlFront = document.getElementById("url-front");
        const textInput = document.getElementById("text-input");
        const urlFull = document.getElementById("url-full");
        const textFileName = document.getElementById("file-name");
        const generateButton = document.getElementById("generate-button");
        const saveButton = document.getElementById("save-button");
        const getIds = document.getElementById("get-ids");
        const autoGen = document.getElementById("auto-gen");
        
        // ��ȸ�� ���̵� ���� �迭�� �ݺ� ���� ���� i
		let ids = "";
		let i = 0;
		
		
		
        
        //url front ���ϴ� �� ������� ����� ���� ������ http:~~~~~~~~ /+ books?id= �� ���س���
        urlFront.value = window.location.href.split('qrgen')[0] + "books/"
        let qrcodeInstance;
		function makeURL(){
			//http:~~~~~~~~ + /books?id= + textInput(id)
			urlFull.value= urlFront.value + textInput.value;
		}
		
		//Ŭ���� qr�ڵ� �������
        generateButton.addEventListener("click", function () {
            const text = urlFront.value + textInput.value;
            if (text) {
                qrcodeDiv.innerHTML = "";
                //���������� qr ����� �κ� qrcodeDiv�� text�� ���� text�� ������� url��
                qrcodeInstance = new QRCode(qrcodeDiv, text);
                //���ϸ� id�� ������ ����
                textFileName.value = text.split('/books/')[1];
                //��ü url ������
                urlFull.value=text;
            }
        });
		//��ư Ŭ���� ������� qr�ڵ尡 �����
        saveButton.addEventListener("click", function () {
            if (qrcodeInstance) {
            	//qrdiv�� �����ϴ� ĵ������ ���ͼ� dataURL�� ��ȯ
                const canvas = qrcodeDiv.querySelector("canvas");
                const imageDataURL = canvas.toDataURL("image/png");

                //a�±� ����� dataURL�� ���ѵ� ������ �ٿ�ε���
                const a = document.createElement("a");
                //a�±׿� �Ӽ� ���� ��ũ
                a.href = imageDataURL;
                //���ϸ�
    			a.download ="qrcode.png"
                if(textFileName.value){
                a.download = textFileName.value+".png";
                textInput.value = null;
                }
                //������ �Ӽ� Ŭ���ؼ� �ٿ�ε�
                a.click();
                textInput.focus();
            }
        });
        
      //���Ǹ� ���� ������ �� �ʿ� ����
        getIds.addEventListener("click",function () {
        	 $.ajax({
                 url: "/tylibrary/get-ids", // ��Ʈ�ѷ� �޼����� URL
                 type: "GET",
                 dataType: "json",
                 success: function (data) {
                     ids = data;
                     console.log(data);
                 },
                 error: function () {
                     alert("�����͸� �������� �߿� ������ �߻��߽��ϴ�.");
                 }
             });
        });
		
		
        autoGen.addEventListener("click", function() {
            let i = 0;
            let interval = setInterval(function() {
                textInput.value = ids[i];
                makeURL();
                if (i === ids.length - 1) { // �迭 �ε����� ���̺��� �ϳ� ������ Ȯ��
                    clearInterval(interval);
                    i = 0;
                } else {
                    i++;
                }
                generateButton.click();
                saveButton.click();
            }, 500);
        });

    </script>
</body>
</html>


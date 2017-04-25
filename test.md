## US账户Return功能
# Table of Contents
1. [Buyer changed mind](#Buyer changed mind)
2. [Example2](#example2-ss)
3. [Third Example](#third-example)  
4. [DVD](#DVD)
5. [Example3](#example3)


## Example
## Example2
## Third Example



## Book/CD Return功能用户界面主要结构：
* Get return instructions only

* Only ask the buyer for more information
  * Item photo	 
  * ISBN
* Specify the reason of return:
  * Buyer changed mind
    * Bought by mistake
    * No longer needed/wanted
    * Better price available
  * Wrong item
    * Title (volume, workbook/textbook/manuel, etc.)
    * Book cover (picture)
    * Condition (new, used-good, etc.)
    * Format (hardcover, paperback, spiral, loose leaf, boardbook, audio, etc.)
    * Edition (1st, 2nd, 3rd, 4th, revised, etc.)
    * Author
    * Year
    * Language
    * ISBN
    
## Example
## Example2 ss
## Third Example
  * Damaged/Defective item
  * Missing parts/accessories
    * CD
    * DVD
    * Access code
    * Other accessories. Please specify: _______
  * Item arrived late
  * Arrived in addition to what was ordered
  * Didn't approve purchase

* Specify the return policy to apply in this order: 	
  * Default	 
  * Return the refund and issue the refund
    * Default
    * Apply 20% restocking fee
    * Item refund
    * Item refund plus shipping fee
    * Item refund plus shipping fee  plus return shipping fee
  * Issue discount without return	 
    * Break-even (default)
    * Other amount ___%
  * Replacement	 

### Buyer changed mind
决策流程图
![return-buyer-changed-mind](images/return-buyer-changed-mind.png "Buyer Changed Mind")    

1. 如果这个订单是灰条，则弹出提示框‘This is a gray label order. Please go to ‘Cancellation Request’。否则执行下一步。

2. 检查deliver date.
   * 如果有tracking number，已经delivered，而且Today - Deliverydate> = 30 days, 则使用邮件模板‘Expired1’
   * 如果没有tracking number, 并且 Today - EDD1>= 30 days, 则使用邮件模板 ‘Expired2’  
   否则执行下一步
3. 如果这个订单是UK FWD订单或者非亚马逊订单，则使用模板‘ReturntoLA’，弹出提示框‘Please record in the googlesheet ‘IBPort Return Record 2015’。否则执行下一步。
## Example3
4. 登录亚马逊买家账户
  * 如果按钮‘Return or Replace Items’不存在
    *  如果‘View return/refund status’按钮存在，则弹出提示框‘A return request was submitted before’
    * 否则，使用邮件模板‘A return request was submitted before’
  * 如果按钮‘Return or Replace Items’存在。根据以下的步骤处理，最后使用模板‘BuyersFault’
  ![return-buyer-fault-senarios](images/return-buyer-fault-senarios.png "return-buyer-fault-senarios")
  * 对以上步骤的解释说明：
    *  Step 1:
    ![](images/return-buyer-fault-step1.png)
    *  Step 2:
    ![](images/return-buyer-fault-step2.png)
    *  Step 3a:  
    It displays ‘This return request will be sent to… for review.’
    ![](images/return-buyer-fault-step3a.png)
    *  Step 4a:
    ![](images/return-buyer-fault-step4a.png)

    * Step 3b:  
    It displayed ‘Once you hit submit, we'll provide a return label so the item can get back to....”
    ![](images/return-buyer-fault-step3b.png)
    * Step 4b:
    ![](images/return-buyer-fault-step4b.png)
    * Step 5b (see Step 5c)
    * Step 6b (see Step 6c)

    * Step 3c:
    ![](images/return-buyer-fault-step3c.png)
    * Step 4c:
    ![](images/return-buyer-fault-step4c.png)
    * Step 5c(5b):
    ![](images/return-buyer-fault-step5c5b.png)
    如果是下图，则选择send to a friend
    ![](images/return-ap-or-pr-seller.jpg)

    * Step 6c(6b):  
    Search the new order # in the designated email account, and obtain the full link by clicking ‘View & Print’.
    ![](images/return-buyer-fault-step6c6b.png)

5. Follow-up system
  * 在场景a中，每隔6小时到buyer gmail尝试得到return lanel，最长时间4天。
  * 如果成功得到return label link
    * 使用'ForInstructions',从后台发出。
    * 向seller gmail发邮件报告，包括原单号，做单号。类型为 ‘Successfully sent a return label to the buyer’,
    * Supplier email format：  
    Subject: Your Return of Amazon.com order 110-0106582-7421800   
    From: order-update@amazon.com   
    Keywords: mailing label
  * 如果只得到了supplier的回复
    * 向seller gmail发邮件报告，包括原单号，做单号。类型为 ‘Successfully sent a return label to the buyer’,
    * Supplier email format  
    Re: Return Requested for order 115-1755109-2673067   
    From: ***@marketplace.amazon.com  
  * 如果不属于以上两种情况
    * 使用‘ReturntoLA’模板向顾客发信。
    * 向seller gmail报告，包括原单号，做单号。类型为
    ‘Seller has not responded to the return requst submitted more than 3 days ago.
    The buyer was instructed to return the item to LA warehouse. Now please record the order in the googlesheet ‘IBPort Return Record 2015'. And contact the supplier or file an A-Z claim for return instructions.’

### Wrong Item
1. 多单情况先以对话框形式让用户选择一个做单单号
2. 如果是UK FWD订单，或者非亚马逊订单，决策树如下。
![](images/return-wrong-item-ukfwd.png)
检查GoogleSheet O列seller price。
 * 如果 seller price <= 20，使用'WrongNoNeed'模板生成邮件，并在亚马逊后台Full Refund
 * 如果 seller price > 20
    * 如果return policy选择了'Default'，使用'WrongDefault'模板生成邮件
    * 如果return policy选择了'Refund'，在亚马逊后台Full Refund, 使用'WrongRefundReturnLA'模板生成邮件.并弹出对话框‘Please record in the googlesheet ‘IBPort Return Record 2015’
    * 如果return policy选择了'Discount'，使用'WrongDiscount'模板生成邮件，在亚马逊后台Issue Discount。并弹出对话框‘The discount is successfully issued.’  
    其中Discount amount =  breakeven discount （取最接近的5的倍数[100 * (G+I-AC)/G]% ）  or the input number
    * 如果 return policy选择了‘Replacement’， 弹出对话框‘Please source a replacement for the buyer and reply the email manually.’
3. 如果这个订单是亚马逊的订单。
 * 决定是哪方的错误  
![](images/return-wrong-item-amazon-order.png)
    * If 2≠3 and 1=2, 1’s fault;
    * If 2≠3 and 1=3, 2’s fault；
    * If 2=3, 3’s fault.  
    对于有多个比较结果，则选择结果的顺序为1>2>3  
    例如，比较conditioan是supllier的错。比较format，是我们的错。则结果是supplier的错。

 * 如果是1 Supplier的错误。则检查GoogleSheet O列seller price。
    * 如果seller price <= 7，使用'WrongNoNeed'模板生成邮件，弹出对话框‘The supplier shipped a wrong item. Please claim a full refund asap! Order information: [buyer account], [supplier order id].’
    * 如果If seller price > 7
      * 如果return policy选择了'Default'。
        * 弹出提示框‘The supplier shipped a wrong item. Please claim a full refund asap! Order information: [buyer account], [supplier order id].’
        * 使用'WrongDefault'模板生成邮件。
      * 如果return policy选择了'Refund'。
        * 登录亚马逊buyer account, 如果‘Return or Replace Items’按钮存在的话，提交return request。 其中return reason是‘Wrong item was sent’，内容是‘Hello, the item you shipped to us is not as described. I wil return it for a full refund including the return shipping fee.”
        * 弹出提示框‘The supplier shipped a wrong item. Please claim a full refund asap! Order information: [buyer account], [supplier order id].’
        * 使用'WrongRefundReturn'邮件模板
      * 如果return policy选择了'Discount'。
        * 使用'WrongDiscount'模板生成邮件，在亚马逊后台Issue Discount。并弹出对话框‘The discount is successfully issued.’  
        * 其中Discount amount =  breakeven discount （取最接近的5的倍数[100 * (G+I-AC)/G]% ）  or the input number
      * 如果 return policy选择了‘Replacement’， 弹出对话框‘Please source a replacement for the buyer and reply the email manually.’

  * 如果是2 Our Listing的错误。则检查GoogleSheet O列seller price。
    * 如果seller price <= 7，使用'WrongNoNeed'模板生成邮件。
    * 如果If seller price > 7
      * 如果return policy选择了‘Default’。
        * 向supplier提交return request，return 原因选择‘no longer needed’
        * 使用‘WrongDefault’生成邮件。
      * 如果return policy选择了‘Refund’。登录亚马逊后台。
        * 如果‘Return or Replace Items’按钮存在，提交return request，return原因选择‘no longer needed’
        * 如果不存在，使用'WrongRefundReturn'生成邮件。
      * 如果return policy选择了'Discount'。
          * 使用'WrongDiscount'模板生成邮件，在亚马逊后台Issue Discount。并弹出对话框‘The discount is successfully issued.’  
          * 其中Discount amount =  breakeven discount （取最接近的5的倍数[100 * (G+I-AC)/G]% ）  or the input number
      * 如果 return policy选择了‘Replacement’， 弹出对话框‘Please source a replacement for the buyer and reply the email manually.’

  * 如果是3 Customer的错误。首先检查deliver date.
    * 如果tracking number存在，并且 Today - DeliveryDate > = 30 days, 则使用W3Expired1模板生成邮件
    * 如果tracking number不存在，并且Today - EDD1 >= 30 days, 则使用W3Expired2模板生成邮件
    * 如果只有condition被比较了，则使用W3BuyersFault模板生成邮件。否则使用W3BuyersFaultCondition
    * 亚马逊订单-Buyer's Fault退货决策树
  ![](images/return-buyer-fault.png)
  * Buyer的收到的book/CD的信息输入方法：
    * Title (volume, workbook/textbook/manuel, etc.) - input box
    * Book cover (picture) - “Does this book match the one the customer received?” Yes/No
    * Condition (new, used-good, etc.) - choose: new/like new/used-very good/used-good/used-acceptable
    * Format: (hardcover, paperback, spiral, loose leaf, boardbook, audio, etc.) - choose: Hardcover/Paperback/Mass Market Paperback/Board Book/Audio CD/Cassette/+ input box
    * Edition(1st, 2nd, 3rd, 4th, revised, etc.) 1st, 2nd, 3rd, revised + input box
    * Author:  input box
    * Year: input box
    * Language: input box
    * ISBN: input box
  * 如何找到书的信息：
    * 通过下面的连接找到condition的信息： https://www.amazon.com/gp/your-account/order-details/ref=oh_aui_or_o00_?ie=UTF8&orderID=102-3179793-7447440
    ![](images/return-condition-info.png)  
    * 点击title后，获得其它的信息。
    ![](images/return-other-info-1.png)
    ![](images/return-other-info-2.png)

### Missing parts/accessories
1. 多单情况,或者UK FWD订单处理原则和Wrong Item相同
2. 如果是亚马逊的订单，首先判断是谁的错：  
![](images/return-missing-part-decision-tree.png)
对于有多个比较结果，则选择结果的顺序为1>2>3

3. 如果是1 supplier的错:
 * 弹出提示框‘The supplier shipped the item without ______(a CD/a DVD/an access code/input box content). Please claim a full refund asap! Order information: [buyer account], [supplier order id].’
 * 检查GoogleSheet O列seller price。
    * 如果seller price <= 7，在亚马逊后台Full Refund。使用'MissingNoNeed'模板生成邮件
    * 如果seller price > 7
      *  如果选择了‘Default’选项，使用MissingDefault模板生成邮件
      *  如果选择了‘Refund’选项，在亚马逊后台Full Refund。然后到buyer account，如果‘Return or Replace Items’按钮存在，则提交return request，return 原因为‘Missing parts or accessories’， 内容为‘Hello, the item you shipped does not include ______(a CD/a DVD/an access code/input box content), which is different from website description. I wil return it for a full refund including the return shipping fee.” 并使用MissingRefundReturn模板生成邮件
      * 如果选择了Discount’选项，则在后台issue discount。生成提示框‘The discount is successfully issued.’ 。使用MissingDiscount生成邮件
        * 其中Discount amount =  breakeven discount （取最接近的5的倍数[100 * (G+I-AC)/G]% ）  or the input number
      * 如果 return policy选择了‘Replacement’， 弹出对话框‘Please source a replacement for the buyer and reply the email manually.’

4. 如果是2 我们的错，则检查GoogleSheet O列seller price。
    * 如果seller price <= 7，使用'MissingNoNeed'模板生成邮件，在后台Full Refund。
    * 如果seller price > 7
      *  如果选择了‘Default’选项，使用MissingDefault模板生成邮件。向supplier 提交return request，return原因为‘no longer needed’
      *  如果选择了‘Refund’选项，则在亚马逊后台Full Refund.登录buyer account,如果‘Return or Replace Items’按钮存在的话，提交return request，return原因为‘no longer needed’。如果没有则继续执行。最终使用 MissingRefundReturn模板生成邮件
      * 如果选择了Discount选项或者Replacement选项，处理方式和supplier的错时相同。
5. 如果是3 顾客的错，首先检查deliver date
   * 如果tracking number存在，并且 Today - DeliveryDate > = 30 days, 则使用M3Expired1模板生成邮件
   * 如果tracking number不存在，并且Today - EDD1 >= 30 days, 则使用M3Expired2模板生成邮件
   * 否则，使用M3BuyersFault生成邮件

### Defective/Damaged Items  
1. 选中Defective/Damaged Items时，Refund - Apply 15% restocking fee 和 replacement两个选项打灰。
2. 决策树如下：
![](images/return-damanged-decision-tree-1.png)
![](images/return-damanged-decision-tree-2.png)
3. 如果选择了‘Default’选项，使用AskCustomer模板生成邮件, Msgbox ‘The supplier shipped a defective/damaged item. Please claim a full refund asap! Order information: [buyer account], [supplier order id].’
4. 如果选择了‘Refund’选项,在supplier gmail里搜索关键词：Subject: Refund on order 102-9825458-4975462
   * 如果邮件存在而且item cost (Googlesheet colume AC) < 20, 使用DFullrefund生成邮件, 在亚马逊后台Full Refund
   * 如果邮件存在而且item cost > = 20, 使用DRefund模板生成邮件, 在亚马逊后台Refund。Refund的额度取决于选择的选项：
     * {Refund - Book refund} = book price
     * {Refund - Book refund plus shipping fee} = full refund
     * {Refund - Book refund plus shipping fee  plus return shipping fee} = full refund
   * 如果邮件不存在， 而且item cost < 20，生成提示框‘The supplier has not
  refunded the order. Please contact the supplier and ask for a full refund’, 使用DFullrefund生成邮件, 在亚马逊后台Full Refund.		
   * 如果邮件不存在， 而且item cost > 20，生成提示框‘The supplier has not refunded the order. Please contact the supplier and ask for a full refund’. 并登陆buyer account，如果‘Return or Replace Items’存在，则提交return rquest, return原因为‘Defective/Does not work properly’，内容为‘Hello, the item you shipped is a defective one that I can’t use, I will return it for a full refund including the return shipping fee.” 使用DRefundReturn模板生成邮件,  在亚马逊后台Refund。Refund的额度与邮件存在的时候相同。
5. 如果选择了‘Discount’选项，在supplier gmail里搜索关键词： 例如：Subject: Refund on order 102-9825458-4975462
   * 如果邮件存在，使用DDiscount生成邮件，在亚马逊后台Issue Discount(discount原则为 break-even 或者根据input number)。
   * 如果邮件不存在，生成对话框The supplier has not refunded the order. Please contact the supplier and ask for a refund’。 使用DDiscount生成邮件，在亚马逊后台Issue Discount (discount原则为 break-even 或者根据input number).

### Item arrived late
选中Defective/Item arrived late时，Refund， Disccount 和 replacement选项均打灰。

### Arrived in addition to what was ordered
使用Keepthebook模板生成邮件

#### Unauthorized purchase
和 Buyer changed mind 处理原则相同

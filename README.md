# AstroTalk
astro talk assignment project

this project is developed on spring boot having maven , spring jpa , h2-database , swagger ui ,  rest apis , spring security with jwt tokens and etc.
please run this project using sts4.

port no : 9090 

to see database run this url :
http://localhost:9090/h2-console/

to see various rest endpoints , run this 
http://localhost:9090/swagger-ui/index.html?


for all the data you need to send using postman to check its working refer to swagger ui.

first add employee i.e. doctor from "/register" 

then get a jwt token using : "/authenticate" by sending username and password using post method

then add a patient and details using :  "/patient/addPatient"  post method

then book an appointment with doctor using : "/patient/bookAppointment"  post method  , a bill is generated for this and a medical history is created.

then check patient all details , bills , medical history using : "/patient/data?id=_" get method.

then add a bed in the hospital using : "/admin/addBed" post method , a bed is created in hospital. three types of bed and prices.

then admit patient using : "/admin/admitPatient" post method , the patient is admitted, if bed is available and he is not already admitted and a bill is added into his account.

check total pending amount on patient using : "/bill/viewTotalPendingBill?id=_"

clear bill using : "/bill/clearBill" by sending amount and patient id.

discharge patient using : "/patient/dischargePatient?id=_" , patient is discharged if he is admitted and there is no pending amount in his account.

check list of all admitted patients using : "/get/allAdmittedPatient" ,


also there are many more features ,that i haven't developed completely but have created a database for them.
i haven't developed it just as an assignment but as a complete project like we can add department , 
operation theatres and book operations for each patient , pathology and book tests for each patient. 
but for now it has limited rest point ends.

in hope you would like my assignment.

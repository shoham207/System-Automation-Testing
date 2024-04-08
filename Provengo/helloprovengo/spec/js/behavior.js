/* @provengo summon selenium */

/**
 * This story opens a new browser window, goes to google.com, and searches for "Pizza".
 */

/*
 bthread("main", function(){
    request(Event("Login"));
    request(Event("select product"));
    request(Event("select x quantity for the product"));
    request(Event("Add product to wishlist"));
    request(Event("admin login"));
    request(Event("admin goes to products list"));
    request(Event("admin select product"));
    request(Event("admin put quantity less than x"));

 })
*/

 /* add a case where admin waits for customer to add product to wishlist */

/*
bthread('Search', function () {
    sync({
        waitFor: Event("select x quantity for the product")
        block: Event("admin put quantity less than x")
    })
})
*/

// with (new SeleniumSession(userSession).start(userURL)) {
//     sync({ request: Event("Start(user_login")})

//     click('//div[2]/div[1]/a[1]/span[1]')
//     writeText('//section[1]/form[1]/div[1]/div[1]/div[1]/input[1]',user.email)
//     writeText('//*[@id="field-password"]',user.password)
//     click('//*[@id="submit-login"]')

//     sync({ request: Event("End(user_login")})
// }

//   ================================= NEW Version =================================
/* =================================== User =================================== */

let userSession
let adminSession

bthread ('user thread', function () {
    userSession = new SeleniumSession(userSessionName).start(userURL)
    user_login(userSession, user)
    add_to_wishlist(userSession, {name: productName, quantity: userQuantity})
    check_quantity_before(userSession, {quantity: userQuantity})
    check_quantity_after(userSession, {quantity: userQuantity})
})

bthread ('admin thread', function () {
    adminSession = new SeleniumSession(adminSessionName).start(adminURL)
    admin_login(adminSession, admin)
    admin_change_quantity(adminSession, {name: productName, quantity: adminQuantity})
})

bthread ('check quantity after add to wish list', function () {
    sync({
        waitFor: Event('End(add_to_wishlist)'),
        block: Event('Start(check_quantity_before)')
//        block: Event('Start(check_quantity_after)')
    })
})

bthread ('admin change quantity after check quantity', function () {
    sync({
//        waitFor: Event('End(admin_login)'),
        waitFor: Event('End(check_quantity_before)'),
        block: Event('Start(admin_change_quantity)')
    })
})

bthread ('check quantity after change quantity', function () {
    sync({
        waitFor: Event('End(admin_change_quantity)'),
        block: Event('Start(check_quantity_after)')
    })
})



/* ================================================================ another option with correction ================================================================*/
/*
let userSession = null
let adminSession = null

bthread ('user login', function () {
    if(userSession === null){
        userSession = new SeleniumSession(userSessionName).start(userURL)
    }
    user_login(userSession, user)
})

bthread ('user add to wish list', function () {
    if(userSession === null){
        userSession = new SeleniumSession(userSessionName).start(userURL)
    }
    add_to_wishlist(userSession, {quantity: userQuantity})
})


bthread ('admin login', function () {
    if(adminSession === null){
        adminSession = new SeleniumSession(adminSessionName).start(adminURL)
    }
    admin_login(adminSession, admin)
})

bthread ('admin change quantity', function () {
    if(adminSession === null){
        adminSession = new SeleniumSession(adminSessionName).start(adminURL)
    }
    change_quantity(adminSession, {name: productName, quantity: adminQuantity})
})

bthread ('correction', function () {
    sync({
        waitFor: Event('End(user_login)'),
        block: Event('Start(add_to_wishlist)')
    })
})

bthread ('correction1', function () {

    sync({
        waitFor: Event('End(admin_login)'),
        block: Event('Start(change_quantity)')
    })
})

bthread ('correction2', function () {

    sync({
        waitFor: Event('End(add_to_wishlist)'),
        block: Event('Start(change_quantity)')
    })
})
*/

























//   ================================= Version 1 =================================

/* =================================== User =================================== */

// let userSession = null
// let adminSession = null

// bthread('userWishList', function(){
//     userSession = new SeleniumSession("userSession").start(userURL)
//     userSession.userLogin({email:'pub@prestashop.com', password: '123456789'})
//     userSession.addToWishList({quantity:userQuantity})
// })



// /* =================================== Admin =================================== */

// bthread('adminLogin', function(){
//     adminSession = new SeleniumSession("adminSession").start(adminURL)
//     adminSession.adminLogin({email:'demo@prestashop.com', password: 'prestashop_demo'})

//     waitFor: Event("End(userAddToWishList)")
//     adminSession.changeQuantity({name: productName,quantity:adminQuantity})


// //    session.

//     /*sync({
//             waitFor: Event()
//             block: Event("admin put quantity less than x")
//         })*/
// })


// /*bthread("Correction",function(){
//     sync({
//         waitFor: Event('End(addToWishList)', {session:userSession, endEvent: true, parameters:{quantity:userQuantity}}),
// //        block: Event('Start(changeQuantity)', {session:adminSession, endEvent: true, parameters:{quantity:adminQuantity}})
//         block: Event('Start(changeQuantity)', {session:adminSession, startEvent: true, parameters:{name: productName,quantity:adminQuantity}})
//     })
// })*/
// bthread("Correction",function(){
//     sync({
//         waitFor: Event('End(addToWishList)', {session:userSession, endEvent: true, parameters:{quantity:userQuantity}}),
//     })
//     // Now, we ensure that 'changeQuantity' does not start until 'addToWishList' has ended.
//     sync({
//         block: adminSession.changeQuantity({name: productName,quantity:adminQuantity})
// //        block: Event('Start(changeQuantity)', {session:adminSession, startEvent: true, parameters:{name: productName, quantity:adminQuantity}})
//     })
// })

/*
bthread('changeQuantity', function () {

})
*/


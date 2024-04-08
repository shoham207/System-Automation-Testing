/* @provengo summon selenium */

const AnyStartInSession = function (s) {
    return bp.EventSet("AnyStartInSession-" + s, function (e) {
        return e.data !== null && e.data.hasOwnProperty('startEvent') && e.data.startEvent && String(s).equals(e.data.session.name)
    })
}

function user_login(session, user) {
    sync({ request: Event('Start(user_login)') })

    session.click('//div[2]/div[1]/a[1]/span[1]')
    session.writeText('//section[1]/form[1]/div[1]/div[1]/div[1]/input[1]',user.email)
    session.writeText('//*[@id="field-password"]',user.password)
    session.click('//*[@id="submit-login"]')

    sync({ request: Event('End(user_login)') })
}

function add_to_wishlist(session, data) {
    sync({ request: Event('Start(add_to_wishlist)') })

//========= OLD METHOD =========// the problem with it that the page changes and then it chooses a different products
//    session.click('//section[1]/section[1]/div[1]/div[8]/article[1]/div[1]/div[1]/a[1]/picture[1]/img[1]') // product img

//========= New METHOD =========//
    session.writeText('//body/main[1]/header[1]/div[2]/div[1]/div[1]/div[2]/div[2]/form[1]/input[2]',data.name, true) // search box
    session.click('//body/ul[1]/li[1]/a[1]/span[1]') // product img

//=======//
    session.writeText('//div[2]/div[1]/div[1]/div[1]/input[1]', data.quantity, true) // quantity box
    session.click('//div[2]/div[1]/button[1]/i[1]') // heart
    session.click('//ul[1]/li[1]/p[1]') // wishlist

    sync({ request: Event('End(add_to_wishlist)') })
}

function admin_login(session, admin) {
    sync({ request: Event('Start(admin_login)') })

    session.writeText('//div[1]/form[1]/div[1]/input[1]', admin.email)
    session.writeText('//div[2]/input[1]', admin.password)
    session.click('//form[1]/div[3]/button[1]')

    sync({ request: Event('End(admin_login)')})
}

/* ================================================================ when window SMALL size enabled ================================================================*/

function admin_change_quantity(session, data) {
    sync({ request: Event('Start(admin_change_quantity)')})

     session.click('//body/header[1]/nav[1]/i[1]') //click the 3 lines
     session.click('//nav[1]/div[1]/ul[1]/li[5]/a') //catalog
     session.click('//nav[1]/div[1]/ul[1]/li[5]/ul[1]/li[1]/a[1]') //Products*/

    session.writeText('//div[1]/form[2]/table[1]/thead[1]/tr[2]/td[4]/input[1]',data.name, true) // search box
    session.click('//div[1]/form[2]/table[1]/thead[1]/tr[2]/td[11]/button[1]') //search product

    session.click('//div[1]/form[2]/table[1]/tbody[1]/tr[1]/td[9]/a[1]') //quantity number
    session.writeText('//div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]',data.quantity, true)

    session.click('//div[3]/form[1]/div[2]/div[1]/div[1]/div[1]/div[2]/button[1]') //save & publish

    sync({ request: Event('End(admin_change_quantity)')})
}


/* ================================================================ when window MAX size enabled ================================================================*/

/*
function change_quantity(session, data) {
    sync({ request: Event('Start(change_quantity)')})


    session.click('//nav[1]/div[1]/ul[1]/li[4]/a') // Catalog
    session.click('//nav[1]/div[1]/ul[1]/li[4]/ul[1]/li[1]/a[1]') //product

    session.writeText('//div[1]/form[2]/table[1]/thead[1]/tr[2]/td[4]/input[1]',data.name, true) // search box
    session.click('//div[1]/form[2]/table[1]/thead[1]/tr[2]/td[11]/button[1]') //search product

    session.click('//div[1]/form[2]/table[1]/tbody[1]/tr[1]/td[9]/a[1]') //quantity number
    session.writeText('//div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]',data.quantity, true)

    session.click('//div[3]/form[1]/div[2]/div[1]/div[1]/div[1]/div[2]/button[1]') //save & publish
    sync({ request: Event('End(change_quantity)')})
}

*/

/* ================================================================ Check Quantity ================================================================*/

function check_quantity_before(session, data) {
    sync({ request: Event('Start(check_quantity_before)')})

    //john profile
    session.click('//div[2]/div[2]/div[1]/a[2]/span[1]')
    //click on My wish list
    session.click('//section[1]/div[1]/div[1]/a[5]/span[1]/i[1]')

    //click on My wish list (2)
    session.click('//section[1]/div[1]/ul[1]/li[1]/a[1]/p[1]')

    //check quantity  -  make sure to clear the wishlist if you are running the program another time.
    session.assertText('//section[1]/ul[1]/li[1]/div[1]/a[1]/div[2]/div[1]/p[1]', 'Quantity : ' + data.quantity)


    sync({ request: Event('End(check_quantity_before)')})
}

function check_quantity_after(session, data) {
    sync({ request: Event('Start(check_quantity_after)')})

    //click back on wishlist again to refresh page
    session.click('//div[1]/nav[1]/ol[1]/li[3]/a[1]/span[1]')
    //click on My wish list (2)
    session.click('//section[1]/div[1]/ul[1]/li[1]/a[1]/p[1]')

    //check quantity  -  this case should fail - you should put the expected quantity number here.
    session.assertText('//section[1]/ul[1]/li[1]/div[1]/a[1]/div[2]/div[1]/p[1]', 'Quantity : 9')

    sync({ request: Event('End(check_quantity_after)')})
}


/*
 *  This is a good place to put common test data, project-wide constants, etc.
 */

const userSessionName = "userSession"
const adminSessionName = "adminSession"

const userURL = 'http://localhost:8080';
const adminURL = 'http://localhost:8080/admina';

let user = { email:'pub@prestashop.com', password: '123456789' }
let admin = { email:'demo@prestashop.com', password: 'prestashop_demo' }

//here you are encouraged to test whatever you like.
// Enter quantity of a product that the user wants to add to the wishlist.
const userQuantity = 10
// Enter quantity of a product that the admin wants to remove from the store.
const adminQuantity = -291
// Name of the product that you want to make the modifications to.
const productName = 'Mug The best is yet to come'

/*
 * NOTE: that in every iteration, you would have to remove the products in the wishlist manually.
 *    so as in the product quantity in the store.
 */

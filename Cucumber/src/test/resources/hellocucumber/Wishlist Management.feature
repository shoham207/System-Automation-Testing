Feature: A set of scenarios for testing the wishList module

  Scenario Outline: User added a product to the wishlist with quantity x
    Given User is in Home Page
    When User is logged in with "<Email>" and "<Password>"
    And User add a product "<Product>" to the wishList with quantity "<X>"
    Then the product should be successfully added to the user's wishlist
    And the quantity is "<X>"
    Examples:
      | Email              | Password  | Product | X |
      | pub@prestashop.com | 123456789 | Customizable mug   | 3 |
      | pub@prestashop.com | 123456789 | Pack Mug + Framed poster   | 20 |


  Scenario Outline: Admin changing product quantity to less than x
    Given Admin is in Home Page
    When Admin is logged in with "<Email>" and "<Password>"
    And admin change the quantity of the product "<Product>" to "<Y>"
    Then the quantity of the product should be "<Y>"
    And the user's wishlist should be adjusted accordingly for scenario <i>
    Examples:
      | Email              | Password  | Product | Y |i |
      | demo@prestashop.com | prestashop_demo | Customizable mug   | -300 |1 |
      #| demo@prestashop.com | prestashop_demo | Customizable mug   | 300 | #for running the test multiple times
      | demo@prestashop.com | prestashop_demo | Pack Mug + Framed poster   | -90 |2|
      #| demo@prestashop.com | prestashop_demo | Pack Mug + Framed poster   | 90 | #for running the test multiple times

  #for running the test multiple times, remove the wishList added item
  #Scenario Outline: The user empties the wishList
    #Given User is in Home Page
    #When User is logged in with "<Email>" and "<Password>"
    #And User remove all product from the wishList
    #Then the product should be successfully removed from the user's wishlist
    #Examples:
      #| Email              | Password  |
      #| pub@prestashop.com | 123456789 |





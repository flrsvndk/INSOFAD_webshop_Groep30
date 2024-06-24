describe("productDetail", () => {
  it("productShouldBeAddedToCartWithOneSpecification", () => {
    cy.intercept('GET', 'http://localhost:8080/api/products', {
      fixture: 'products.json'
    }).as('getProductsRequest');
    cy.intercept('GET', 'http://localhost:8080/api/products/2ece2b60-a3b3-4d1d-9128-ba6014153978', {
      fixture: '2ece2b60-a3b3-4d1d-9128-ba6014153978.json'
    })
    let expectedProductType = "silver";

    cy.visit('http://localhost:4200/products')
    cy.contains('Show Detail').click()
    cy.url().should('include', '/products/')
    cy.contains(expectedProductType).click()
    cy.get(':button').should('not.be.disabled')
    cy.contains('button', 'Voeg toe aan winkelmand').click()
    cy.visit('http://localhost:4200/cart')
    cy.contains(expectedProductType)
  })

  it("productShouldNotBeAddedToCartWithOnlyOneSpecification", () => {
    cy.intercept('GET', 'http://localhost:8080/api/products/54c47356-f420-468a-a112-7979c34b096e', {
      fixture: '54c47356-f420-468a-a112-7979c34b096e.json'
    }).as('getProductRequest');
    let expectedProductType = "Polyester";
    cy.visit('http://localhost:4200/products/54c47356-f420-468a-a112-7979c34b096e')
    cy.contains(expectedProductType).click()
    cy.get(':button').should('be.disabled')
  })

})
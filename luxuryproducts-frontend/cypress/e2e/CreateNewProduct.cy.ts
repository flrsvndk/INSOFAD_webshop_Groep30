describe('Create New Product Form', () => {
  beforeEach(() => {
    let userToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJIb3Jpem9uIERldmVsb3BtZW50cyIsImV4cCI6MTcxOTI4NzcwMCwiaWF0IjoxNzE5MjY2MTAwLCJlbWFpbCI6ImJvYkBib2JzbHV4dXJ5ZW50ZXJwcmlzZS5jb20ifQ.93eHhp575onquMcP2PPDHay8Ib-2XWrVE3IzoOT4h-k";
    window.localStorage.setItem('token', userToken);
    cy.visit('/admin/products/new');
  });

  it('should have the correct form fields', () => {
    cy.get('h2').should('contain', 'Create New Product');
    cy.get('#prodName').should('exist');
    cy.get('#description').should('exist');
    cy.get('#categoryId').should('exist');
    cy.get('#specName').should('exist');
  });

  it('should allow input in form fields', () => {
    cy.get('#prodName').type('Sample Product').should('have.value', 'Sample Product');
    cy.get('#description').type('This is a sample product description').should('have.value', 'This is a sample product description');
    cy.get('#categoryId').select('1').should('have.value', '1'); // Adjust value based on actual options
    cy.get('#specName').type('Sample Specification').should('have.value', 'Sample Specification');

    cy.get('button').contains('Add new Type').click();

    cy.get('#name0').type('Type 1').should('have.value', 'Type 1');
    cy.get('#price0').type('100').should('have.value', '100');
    cy.get('#stock0').type('50').should('have.value', '50');
    cy.get('#imgUrl0').type('http://example.com/image.jpg').should('have.value', 'http://example.com/image.jpg');
  });

  it('should allow adding and removing types', () => {
    cy.get('button').contains('Add new Type').click();
    cy.get('.card-title').should('contain', 'Type 2');
    cy.get('button').contains('Remove Type').click();
    cy.get('Type 2').should('not.exist');
  });

  it('should submit the form', () => {
    cy.intercept('POST', 'http://localhost:8080/api/products', {
      fixture: '2ece2b60-a3b3-4d1d-9128-ba6014153978.json'
    })

    cy.get('#prodName').type('Sample Product');
    cy.get('#description').type('This is a sample product description');
    cy.get('#categoryId').select('1');
    cy.get('#specName').type('Sample Specification');
    cy.get('#name0').type('Type 1');
    cy.get('#price0').type('100');
    cy.get('#stock0').type('50');
    cy.get('#imgUrl0').type('http://example.com/image.jpg');

    cy.get('button').contains('Create Product').click();

    cy.url().should('include', '/admin/product');
  });
});

describe('template spec', () => {

  beforeEach(() => {
    cy.intercept('POST', '/api/auth/login', { fixture: 'login.json' }).as('login');
    cy.intercept('GET', '/api/auth/user', { fixture: 'user.json' }).as('user');

    cy.visit('http://localhost:4200/auth/login');
    cy.get('input[id=email]').type('bob@bobsluxuryenterprise.com');
    cy.get('input[id=password1]').type('MegaGeheim6789!!');
    cy.get('button[id=login]').click();
  });


  it('passes', () => {
    //testing giftcards
    cy.intercept('POST', '/api/giftcards', { fixture: 'example.json' }).as('login');
    cy.visit("http://localhost:4200/giftcards");
    cy.contains("Frequently Asked Questions");
    cy.get(".btn").should('be.disabled')
    cy.get('[type="email"]').type('bob@bobsluxuryenterprise.com')
    cy.get(".btn").last().click()

    // testing my-giftcards
    cy.intercept('GET', '/api/giftcards/getByOwner', { fixture: 'getByOwner.json' }).as('getByOwner');
    cy.intercept('GET', '/api/giftcards/getByBuyer', { fixture: 'getByBuyer.json' }).as('getByBuyer');
    cy.intercept('PUT', '/api/charge/', { fixture: 'example.json' }).as('charge');
    cy.visit("http://localhost:4200/my-giftcards");
    cy.contains("Bought");
    cy.contains("Sent");
    cy.get("button").last().click();

    // testing admin-giftcards
    cy.intercept('GET', '/api/giftcards/getAll', { fixture: 'getAll.json' }).as('getAll');
    cy.intercept('DELETE', '/api/giftcards/**', { fixture: 'example.json' }).as('delete');
    cy.visit("http://localhost:4200/admin/giftcards");
    cy.contains("Bought");
    cy.contains("Sent");
    cy.get("button").last().click();
  })
})
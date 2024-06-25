describe('giftcards', () => {

  beforeEach(() => {
    cy.intercept('POST', '/api/auth/login', { fixture: 'login.json' }).as('login');
    cy.intercept('GET', '/api/auth/user', { fixture: 'user.json' }).as('user');

    cy.visit('http://localhost:4200/auth/login');
    cy.get('input[id=email]').type('bob@bobsluxuryenterprise.com');
    cy.get('input[id=password1]').type('MegaGeheim6789!!');
    cy.get('button[id=login]').click();

    cy.intercept('POST', '/api/giftcards', { fixture: 'example.json' }).as('login');
    cy.intercept('GET', '/api/giftcards/getByOwner', {fixture: 'getByOwner.json'}).as('getByOwner');
    cy.intercept('GET', '/api/giftcards/getByBuyer', {fixture: 'getByBuyer.json'}).as('getByBuyer');
    cy.intercept('PUT', '/api/charge/', {fixture: 'example.json'}).as('charge');
    cy.intercept('GET', '/api/giftcards/getAll', {fixture: 'getAll.json'}).as('getAll');
    cy.intercept('DELETE', '/api/giftcards/**', {fixture: 'example.json'}).as('delete');
  });

  //testing giftcards
  it('Giftcard FAQ', () => {
    cy.visit("http://localhost:4200/giftcards");
    cy.contains("Frequently Asked Questions");
  });

  it('Giftcard kopen', () => {
    cy.visit("http://localhost:4200/giftcards");
    cy.get(".btn").should('be.disabled')
    cy.get('[type="email"]').type('bob@bobsluxuryenterprise.com')
    cy.get(".btn").last().click()
  });

  // testing my-giftcards
  it('Gekochte giftcards inzien, gekregen giftcards inzien', () => {
    cy.visit("http://localhost:4200/my-giftcards");
    cy.contains("Bought");
    cy.contains("Sent");
  });

  it('Giftcard opladen', () => {
    cy.visit("http://localhost:4200/my-giftcards");
    cy.get("button").last().click();
  });

  // testing admin-giftcards
  it('Alle giftcards inzien', () => {
    cy.visit("http://localhost:4200/admin/giftcards");
    cy.contains("Bought");
    cy.contains("Sent");
  });

  it('Giftcard annuleren', () => {
    cy.visit("http://localhost:4200/admin/giftcards");
    cy.get("button").last().click();
  });
})
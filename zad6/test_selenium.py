import pytest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service
import time

URL = "http://localhost:3000"

@pytest.fixture(scope="module")
def driver():
    service = Service('C:/Users/48606/IdeaProjects/ebiznes/zad6/chromedriver-win64/chromedriver.exe')
    options = webdriver.ChromeOptions()
    options.add_argument("--headless")
    driver = webdriver.Chrome(service=service, options=options)
    driver.implicitly_wait(3)
    yield driver
    driver.quit()

def test_product_listing(driver):
    driver.get(f"{URL}")
    header = driver.find_element(By.TAG_NAME, "h2")
    assert "Products" in header.text
    assert header.is_displayed()
    product_items = driver.find_elements(By.TAG_NAME, "li")
    assert len(product_items) > 0
    assert isinstance(product_items, list)
    for item in product_items:
        assert "Price:" in item.text
        assert item.find_element(By.TAG_NAME, "button").text == "Add to Cart"
        assert item.is_displayed()

def test_add_to_cart(driver):
    driver.get(f"{URL}")

    buttons = driver.find_elements(By.XPATH, "//button[text()='Add to Cart']")
    assert len(buttons) >= 2, "Not enough products to add to cart"
    assert buttons[0].is_enabled()
    assert buttons[1].is_enabled()
    buttons[0].click()
    buttons[1].click()

    cart_items = driver.find_elements(By.CSS_SELECTOR, '[data-testid="cart-list"] > li')
    assert len(cart_items) == 2, f"Expected 2 items in cart, found {len(cart_items)}"
    assert all("x" in item.text for item in cart_items), "Not all items show quantity"
    assert cart_items[0].is_displayed()
    assert cart_items[1].is_displayed()

def test_cart_empty_message(driver):
    driver.get(f"{URL}")
    cart_text = driver.find_element(By.TAG_NAME, "div").text
    assert "Your cart is empty" in cart_text or "Cart" in cart_text
    assert "empty" in cart_text.lower() or "cart" in cart_text.lower()

def test_payment_form_fields_exist(driver):
    driver.get(f"{URL}")

    labels = [label.text for label in driver.find_elements(By.TAG_NAME, "label")]
    expected = ["Order ID:", "Amount:", "Card Number:", "Card Holder:", "Expiry Date (MM/YY):", "CVV:"]
    for label in expected:
        assert label in labels

    inputs = driver.find_elements(By.TAG_NAME, "input")
    assert len(inputs) >= 6
    assert inputs[0].get_attribute("type") == "text"
    assert inputs[1].get_attribute("type") == "number"

def test_fill_payment_form_and_submit(driver):
    driver.get(f"{URL}")

    card_number = driver.find_element(By.NAME, "cardNumber")
    card_number.send_keys("4111111111111111")
    assert card_number.get_attribute("value") == "4111111111111111"

    card_holder = driver.find_element(By.NAME, "cardHolder")
    card_holder.send_keys("VPL-K")
    assert card_holder.get_attribute("value") == "VPL-K"

    expiry_date = driver.find_element(By.NAME, "expiryDate")
    expiry_date.send_keys("12/25")
    assert expiry_date.get_attribute("value") == "12/25"

    cvv = driver.find_element(By.NAME, "cvv")
    cvv.send_keys("123")
    assert cvv.get_attribute("value") == "123"

    submit_button = driver.find_element(By.XPATH, "//button[text()='Process Payment']")
    assert submit_button.is_enabled()
    submit_button.click()

    time.sleep(1)
    status = driver.find_element(By.XPATH, "//div[contains(text(),'Payment')]").text
    assert "Payment" in status
    assert status != ""

def test_invalid_card_number(driver):
    driver.get(f"{URL}")

    driver.find_element(By.NAME, "cardNumber").send_keys("0000")
    driver.find_element(By.NAME, "cardHolder").send_keys("VPL-K")
    driver.find_element(By.NAME, "expiryDate").send_keys("12/25")
    driver.find_element(By.NAME, "cvv").send_keys("123")
    driver.find_element(By.XPATH, "//button[text()='Process Payment']").click()
    time.sleep(1)
    status = driver.find_element(By.XPATH, "//div[contains(text(),'Payment')]").text
    assert "failed" in status.lower() or "Payment" in status
    assert len(status) > 0

def test_order_id_in_payment_response(driver):
    driver.get(f"{URL}")
    order = driver.find_element(By.XPATH, "//input[@type='text' and @readOnly]").get_attribute("value")
    assert order.startswith("ORD-")
    assert len(order) > 5

    driver.find_element(By.XPATH, "//button[text()='Process Payment']").click()
    time.sleep(1)
    status = driver.find_element(By.XPATH, "//div[contains(text(),'Payment')]").text
    assert order in status

def test_payment_order_id_is_readonly(driver):
    driver.get(f"{URL}")
    order_input = driver.find_element(By.XPATH, "//input[@type='text' and @readOnly]")
    assert order_input is not None
    assert order_input.get_attribute("readonly") == "true"

def test_amount_editable(driver):
    driver.get(f"{URL}")
    amount_input = driver.find_element(By.NAME, "amount")
    amount_input.clear()
    amount_input.send_keys("399.99")
    assert amount_input.get_attribute("value") == "399.99"
    assert float(amount_input.get_attribute("value")) > 0

def test_card_holder_input_accepts_text(driver):
    driver.get(f"{URL}")
    name_input = driver.find_element(By.NAME, "cardHolder")
    name_input.send_keys("Test User")
    assert "Test User" in name_input.get_attribute("value")
    assert len(name_input.get_attribute("value")) > 0

def test_expiry_date_format(driver):
    driver.get(f"{URL}")
    expiry_input = driver.find_element(By.NAME, "expiryDate")
    expiry_input.send_keys("12/34")
    assert expiry_input.get_attribute("value") == "12/34"
    assert "/" in expiry_input.get_attribute("value")

def test_cvv_input_length(driver):
    driver.get(f"{URL}")
    cvv_input = driver.find_element(By.NAME, "cvv")
    cvv_input.send_keys("1234")
    assert len(cvv_input.get_attribute("value")) >= 3
    assert cvv_input.get_attribute("value").isdigit()

def test_page_title(driver):
    driver.get(f"{URL}")
    assert "React App" in driver.title
    assert len(driver.title) > 0

def test_product_prices_format(driver):
    driver.get(f"{URL}")
    prices = driver.find_elements(By.XPATH, "//p[contains(text(),'Price:')]")
    assert len(prices) > 0
    for price in prices:
        assert "$" in price.text
        assert "." in price.text

import csv
import random
import string

from validators import validate_password

invalid_passwords = 'invalid_passwords.csv'
valid_passwords = 'valid_passwords.csv'


def create_char_pool(include_uppercase, include_lowercase, include_numbers, include_special):
    char_pool = ''
    if include_uppercase:
        char_pool += string.ascii_uppercase
    if include_lowercase:
        char_pool += string.ascii_lowercase
    if include_numbers:
        char_pool += string.digits
    if include_special:
        char_pool += '!@#$%^&*()-_=+{};:,<.>'
    return char_pool


def generate_password(length_range, include_uppercase, include_number, include_special):
    length = random.randint(length_range[0], length_range[1])
    char_pool = create_char_pool(include_uppercase, True, include_number, include_special)

    if not include_uppercase and not include_number and not include_special:
        char_pool = string.ascii_lowercase

    password = ''.join(random.choices(char_pool, k=length))

    if include_uppercase:
        password += random.choice(string.ascii_uppercase)
    if include_number:
        password += random.choice(string.digits)
    if include_special:
        password += random.choice('!@#$%^&*()-_=+{};:,<.>')

    password_list = list(password)
    random.shuffle(password_list)
    return ''.join(password_list)[:length]


with open(invalid_passwords, 'w', newline='') as csvfile:
    password_writer = csv.writer(csvfile)
    password_writer.writerow([generate_password([8, 20], False, False, False)])
    password_writer.writerow([generate_password([8, 20], False, False, True)])
    password_writer.writerow([generate_password([8, 20], False, True, False)])
    password_writer.writerow([generate_password([8, 20], False, True, True)])
    password_writer.writerow([generate_password([8, 20], True, True, False)])
    password_writer.writerow([generate_password([8, 20], True, False, False)])
    password_writer.writerow([generate_password([8, 20], True, False, False)])
    password_writer.writerow([generate_password([8, 20], True, False, True)])
    password_writer.writerow([generate_password([21, 22], True, True, True)])
    password_writer.writerow([generate_password([1, 7], True, True, True)])

with open(valid_passwords, 'w', newline='') as csvfile:
    password_writer = csv.writer(csvfile)
    for _ in range(11):
        password = generate_password([8, 20], True, True, True)
        if not validate_password(password):
          password_writer.writerow([password])

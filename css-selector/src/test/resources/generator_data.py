import csv
import random
import string
from validators import validate_email


def create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols, include_host):
    char_pool = ''
    if include_uppercase:
        char_pool += string.ascii_uppercase
    if include_lowercase:
        char_pool += string.ascii_lowercase
    if include_numbers:
        char_pool += string.digits
    if include_symbols:
        char_pool += '._'
    if include_host:
        char_pool += string.ascii_lowercase
    return char_pool


def generate_local(length, include_uppercase=True, include_lowercase=True, include_numbers=True, include_symbols=True):
    char_pool = create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols, False)
    local_part = random.choice(string.ascii_lowercase)  # Start with a letter
    if length > 1:
        local_part += ''.join(random.choices(char_pool, k=length-1))
    return local_part


def generate_domain(length, include_uppercase=False, include_lowercase=True, include_numbers=False, include_symbols=False):
    char_pool = create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols, False)
    return ''.join(random.choices(char_pool, k=length))


def generate_host(length, include_uppercase=False, include_lowercase=True, include_numbers=False, include_symbols=False):
    char_pool = create_char_pool(include_uppercase, include_lowercase, include_numbers, include_symbols, True)
    return ''.join(random.choices(char_pool, k=length))


def generate_email(local_length, host_length, domain_length):
    local_part = generate_local(local_length, True, True, True, True)
    host_part = generate_host(host_length, False, True, False, False)
    domain_part = generate_domain(domain_length, False, True, False, False)
    return f"{local_part}@{host_part}.{domain_part}"


def generate_incorrect_email():
    incorrect_emails = []
    incorrect_emails.append(random.choice(string.digits + '._') + ''.join(random.choices(string.ascii_letters + string.digits, k=random.randint(1, 19))) + '@' + generate_domain(5))
    incorrect_emails.append('.' + ''.join(random.choices(string.ascii_letters + string.digits, k=random.randint(1, 19))) + '@' + generate_domain(5))
    incorrect_emails.append(''.join(random.choices(string.ascii_letters, k=random.randint(1, 19))) + '@' + ''.join(random.choices(string.ascii_letters + string.digits + '._', k=random.randint(1, 10))) + '.' + ''.join(random.choices(string.ascii_letters, k=random.randint(1, 10))))
    incorrect_emails.append(''.join(random.choices(string.ascii_letters, k=random.randint(1, 19))) + '@' + ''.join(random.choices(string.ascii_letters, k=random.randint(1, 10))) + '.' + random.choice(string.ascii_letters))
    incorrect_emails.append(''.join(random.choices(string.ascii_letters, k=random.randint(1, 19))) + '@' + ''.join(random.choices(string.ascii_letters, k=random.randint(1, 10))))
    return random.choice(incorrect_emails)


def generate_emails(total_lengths, local_lengths, domain_lengths, host_lengths, upper, digit, start_a_z, host_a_z, domain_a_z, at_symbol, special_symbols, filename):
    with open(filename, 'w', newline='') as csvfile:
        email_writer = csv.writer(csvfile)
        for total_length in total_lengths:
            for local_length in local_lengths:
                for domain_length in domain_lengths:
                    for host_length in host_lengths:
                        if (local_length + host_length + domain_length + 2) <= total_length:
                            email = generate_email(local_length, host_length, domain_length)
                            incorrect_email = generate_incorrect_email()
                            if not validate_email(incorrect_email):
                                email_writer.writerow([email])
                                email_writer.writerow([incorrect_email])


total_lengths = range(1, 21)
local_lengths = range(1, 10)
domain_lengths = range(2, 10)
host_lengths = range(1, 10)
upper = True
digit = True
start_a_z = True
host_a_z = True
domain_a_z = True
at_symbol = True
special_symbols = True
filename = 'email.csv'

generate_emails(total_lengths, local_lengths, domain_lengths, host_lengths, upper, digit, start_a_z, host_a_z, domain_a_z, at_symbol, special_symbols, filename)

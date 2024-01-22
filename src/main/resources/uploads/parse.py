from bs4 import BeautifulSoup
import json
import sys

def parse_html(file_path):
    f = open(file_path, "r")
    htmltxt = f.read()
    parsed_html = BeautifulSoup(htmltxt,features="lxml")
    all_items = []
    items_cost = {}
    items_qty = {}
    filename=""
    total_items_cost = 0

    for item in parsed_html.find_all("div", {"class": "print-bill-subheader"}):
        filename+=item.findChild("h1", {"class": "print-bill-date"}).string.split("order")[0].strip()
        filename+="/"
        filename+=item.findChild("div", {"class": "print-bill-bar-id"}).string.split("#")[1].strip()

    for item in parsed_html.find_all("div", {"class": "dn print-items-list"}):
        item_name = item.findChild("div", {"class":"pv2 flex justify-between"}).findChild("div", {"class":"w_U9_0 w_sD6D w_QcqU"}).string
        item_cost = float(item.findChild("div", {"class":"print-bill-price"}).findChild("div", {"class":"w_U9_0 w_sD6D w_QcqU"}).string[1:]);
        item_qty = int(item.findChild("div", {"class":"print-bill-qty"}).findChild("div", {"class":"w_U9_0 w_sD6D w_QcqU"}).string.split(" ")[1])
        curr_item = {}
        curr_item["name"]=item_name
        curr_item["quantity"]=item_qty
        curr_item["price"]=float(item_cost)/item_qty
        if item.findChild("div", {"class":"print-bill-type"}).findChild("div", {"class":"w_U9_0 w_sD6D w_QcqU"}).string == "Unavailable":
            items_cost[item_name]=0
            curr_item["price"]=0
        total_items_cost+=curr_item["price"]*curr_item["quantity"]
        all_items.append(curr_item)

    for item in parsed_html.find_all("div", {"class": "bill-order-total-payment"}):
        extras = {}
        extras["name"] = "Tip, taxes and delivery fees"
        extras["price"] = float(item.findChildren("h2")[1].string[1:]) - total_items_cost
        extras["quantity"] = 1
        all_items.append(extras)

    with open(sys.argv[1]+'_items.json', 'w') as fp:
        json.dump(all_items, fp)
    
    print(filename)

parse_html(sys.argv[1])